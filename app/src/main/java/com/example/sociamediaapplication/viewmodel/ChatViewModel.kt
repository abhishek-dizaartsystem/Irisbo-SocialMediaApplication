package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.preferences.SocketManager
import com.example.sociamediaapplication.data.repository.ChatRepository
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.model.UploadMedia
import com.example.sociamediaapplication.model.response.Attachment
import com.example.sociamediaapplication.model.response.ConversationDetailsResponse
import com.example.sociamediaapplication.model.response.ConversationsResponse
import com.example.sociamediaapplication.model.response.MessageResponse
import com.example.sociamediaapplication.model.response.MessagesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _mediaList = MutableStateFlow<List<UploadMedia>>(emptyList())
    val mediaList: StateFlow<List<UploadMedia>> = _mediaList

    private val _conversations = MutableStateFlow<ConversationsResponse?>(null)
    val conversations: StateFlow<ConversationsResponse?> = _conversations

    private val _messages = MutableStateFlow<MessagesResponse?>(null)
    val messages: StateFlow<MessagesResponse?> = _messages

    private val _conversationDetails = MutableStateFlow<ConversationDetailsResponse?>(null)
    val conversationDetails: StateFlow<ConversationDetailsResponse?> = _conversationDetails

    private val _onlineUsers = MutableStateFlow<Set<Int>>(emptySet())
    val onlineUsers: StateFlow<Set<Int>> = _onlineUsers

    private val _lastSeenMap = MutableStateFlow<Map<Int, String>>(emptyMap())
    val lastSeenMap: StateFlow<Map<Int, String>> = _lastSeenMap

    private var _isListeningConversations = false

    private var currentPage = 1
    private var isLoadingMore = false
    private var hasMore = true

    private val _scrollToBottom = MutableStateFlow(false)
    val scrollToBottom: StateFlow<Boolean> = _scrollToBottom

    private val _otherUserLastReadMessageId = MutableStateFlow<Int?>(null)
    val otherUserLastReadMessageId: StateFlow<Int?> = _otherUserLastReadMessageId

    private val _prependedCount = MutableStateFlow(0)
    val prependedCount: StateFlow<Int> = _prependedCount

    // Tracks message ids added locally via REST so socket doesn't duplicate them
    private val locallyAddedMessageIds = mutableSetOf<Int>()

    fun notifyScrollToBottom() {
        _scrollToBottom.value = true
    }

    fun consumeScrollToBottom() {
        _scrollToBottom.value = false
    }

    fun consumePrependedCount() {
        _prependedCount.value = 0
    }

    fun deleteMessage(messageId: Int) {
        val socket = SocketManager.getSocket()
        val json = JSONObject()
        json.put("messageId", messageId)
        socket?.emit("message:delete", json)
    }

    fun observeDeleteMessages(conversationId: Int) {
        val socket = SocketManager.getSocket()
        socket?.off("message:deleted")

        socket?.on("message:deleted") { args ->
            val data = args[0] as JSONObject
            val deletedMessage = data.getJSONObject("data")

            val deletedId = deletedMessage.optInt("messageId")
            val msgConversationId = deletedMessage.optInt("conversationId")

            viewModelScope.launch {
                val currentMessages = _messages.value
                if (currentMessages != null && msgConversationId == conversationId) {
                    _messages.value = currentMessages.copy(
                        messages = currentMessages.messages.filter { it.id != deletedId }
                    )
                }

                val currentConversations = _conversations.value
                if (currentConversations != null) {
                    val updatedConvs = currentConversations.conversations.map { conv ->
                        if (conv.conversation_id == msgConversationId &&
                            conv.last_message_id == deletedId
                        ) {
                            conv.copy(content = "Message deleted", last_message_id = null)
                        } else conv
                    }
                    _conversations.value = currentConversations.copy(conversations = updatedConvs)
                }
            }
        }
    }

    fun observeConversationUpdates(uid: Int) {
        if (_isListeningConversations) return
        _isListeningConversations = true

        val socket = SocketManager.getSocket()

        socket?.on("conversation:updated") { args ->
            val data = args[0] as JSONObject
            val summaryJson = data.getJSONObject("data")

            val updatedConversationId = summaryJson.optInt("conversation_id", -1)
            val lastMsg = summaryJson.optJSONObject("last_message")
            val senderId = lastMsg?.optInt("sender_id", -1)

            viewModelScope.launch {
                val current = _conversations.value ?: return@launch

                val updatedList = current.conversations.map { conv ->
                    if (conv.conversation_id == updatedConversationId) {
                        val shouldMarkUnread = senderId != uid
                        conv.copy(
                            last_message_id = lastMsg?.optInt("id") ?: conv.last_message_id,
                            content = lastMsg?.optString("content") ?: conv.content,
                            last_message_at = lastMsg?.optString("created_at") ?: conv.last_message_at,
                            last_sender_id = senderId,
                            last_read_message_id = if (shouldMarkUnread) {
                                conv.last_read_message_id
                            } else {
                                lastMsg?.optInt("id")
                            }
                        )
                    } else conv
                }

                _conversations.value = current.copy(
                    conversations = updatedList.sortedByDescending { it.last_message_at }
                )
            }
        }
    }

    fun observePresence() {
        val socket = SocketManager.getSocket()

        socket?.on("presence:online") { args ->
            val data = args[0] as JSONObject
            _onlineUsers.value = _onlineUsers.value + data.optInt("userId")
        }

        socket?.on("presence:offline") { args ->
            val data = args[0] as JSONObject
            val userId = data.optInt("userId")
            val lastSeen = data.optString("lastSeenAt")
            _onlineUsers.value = _onlineUsers.value - userId
            _lastSeenMap.value = _lastSeenMap.value + (userId to lastSeen)
        }
    }

    fun observeSocketMessages(conversationId: Int, uid: Int) {
        val socket = SocketManager.getSocket()
        socket?.off("message:new")

        socket?.on("message:new") { args ->
            val data = args[0] as JSONObject
            val messageJson = data.getJSONObject("data")

            Log.d("SOCKET_DEBUG", messageJson.toString())

            val msgConversationId = messageJson.optInt("conversation_id", -1)
            if (msgConversationId != conversationId) return@on

            val incomingId = messageJson.optInt("id", -1)

            viewModelScope.launch {
                val current = _messages.value ?: return@launch

                // Skip if already added locally via REST (media/audio send)
                if (locallyAddedMessageIds.contains(incomingId)) {
                    locallyAddedMessageIds.remove(incomingId)
                    return@launch
                }

                val attachmentsArray = messageJson.optJSONArray("attachments")
                val parsedAttachments = mutableListOf<Attachment>()

                if (attachmentsArray != null) {
                    for (i in 0 until attachmentsArray.length()) {
                        val obj = attachmentsArray.getJSONObject(i)
                        parsedAttachments.add(
                            Attachment(
                                id = obj.optInt("id"),
                                message_id = obj.optInt("message_id"),
                                file_url = obj.optString("file_url"),
                                file_type = obj.optString("file_type"),
                                mime_type = obj.optString("mime_type"),
                                original_name = obj.optString("original_name"),
                                file_size = obj.optInt("file_size"),
                                created_at = obj.optString("created_at")
                            )
                        )
                    }
                }

                val newMsg = MessageResponse(
                    id = incomingId,
                    conversation_id = msgConversationId,
                    sender_id = messageJson.optInt("sender_id", -1),
                    message_type = messageJson.optString("message_type", "text"),
                    content = messageJson.optString("content", ""),
                    reply_to_message_id = null,
                    client_temp_id = null,
                    is_edited = 0,
                    edited_at = null,
                    is_deleted = 0,
                    deleted_at = null,
                    created_at = messageJson.optString("created_at", ""),
                    updated_at = messageJson.optString("created_at", ""),
                    sender_name = messageJson.optString("sender_name", ""),
                    sender_username = messageJson.optString("sender_username", ""),
                    sender_profile_image = messageJson.optString("sender_profile_image", ""),
                    reply_message_id = null,
                    reply_message_content = null,
                    reply_message_type = null,
                    reply_message_sender_id = null,
                    attachments = parsedAttachments
                )

                _messages.value = current.copy(messages = current.messages + newMsg)
                notifyScrollToBottom()

                // If incoming from other person, mark read
                if (messageJson.optInt("sender_id", -1) != uid) {
                    markConversationReadSocket(conversationId)
                }
            }
        }
    }

    fun sendMessage(conversationId: Int, text: String) {
        val socket = SocketManager.getSocket()
        val json = JSONObject()
        json.put("conversationId", conversationId)
        json.put("content", text)
        socket?.emit("message:send", json)
    }

    fun addImage(uri: Uri) {
        _mediaList.update { current -> current + UploadMedia(uri, MediaType.IMAGE) }
    }

    fun addVideo(uri: Uri) {
        _mediaList.update { current -> current + UploadMedia(uri, MediaType.VIDEO) }
    }

    fun removeMedia(media: UploadMedia) {
        _mediaList.update { current -> current - media }
    }

    fun fetchConversations() {
        viewModelScope.launch {
            try {
                _conversations.value = repository.getInbox()
                Log.d("ChatVM_Debug", _conversations.value.toString())
            } catch (e: Exception) {
                Log.e("Chat_VM", e.message.toString())
            }
        }
    }

    fun fetchMessages(conversationId: Int) {
        currentPage = 1
        hasMore = true
        _otherUserLastReadMessageId.value = null

        viewModelScope.launch {
            try {
                val response = repository.getMessages(conversationId, currentPage)
                Log.d("FetchMsg_DEBUG", response.pagination.toString())
                hasMore = response.pagination.page < response.pagination.totalPages
                _messages.value = response
            } catch (e: Exception) {
                Log.e("Chat_VM", e.message.toString())
            }
        }
    }

    fun loadMoreMessages(conversationId: Int) {
        if (isLoadingMore || !hasMore) return
        isLoadingMore = true
        currentPage++

        viewModelScope.launch {
            try {
                val response = repository.getMessages(conversationId, currentPage)
                val current = _messages.value ?: return@launch

                val combined = response.messages + current.messages
                val unique = combined.distinctBy { it.id }

                _messages.value = current.copy(messages = unique)

                val addedCount = unique.size - current.messages.size
                if (addedCount > 0) _prependedCount.value = addedCount
                if (response.messages.isEmpty()) hasMore = false

            } catch (e: Exception) {
                currentPage--
                Log.e("Chat_VM", e.message.toString())
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun fetchConversationDetails(conversationId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getConversationDetails(conversationId)
                _conversationDetails.value = response
                response.data.last_read_message_id.let {
                    _otherUserLastReadMessageId.value = it
                }
            } catch (e: Exception) {
                Log.e("Chat_VM", e.message.toString())
            }
        }
    }

    fun markConversationRead(conversationId: Int) {
        viewModelScope.launch {
            try {
                repository.markConversationRead(conversationId)
            } catch (e: Exception) {
                Log.e("Chat_VM", e.message.toString())
            }
        }
    }

    fun markConversationReadSocket(conversationId: Int) {
        val socket = SocketManager.getSocket()
        val json = JSONObject()
        json.put("conversationId", conversationId)
        socket?.emit("conversation:read", json)
    }

    private var _isListeningReadUpdates = false

    fun observeReadUpdates(currentUserId: Int, currentConversationId: Int) {
        if (currentUserId == 0) return

        val socket = SocketManager.getSocket()
        socket?.off("conversation:read:update")
        _isListeningReadUpdates = false

        if (_isListeningReadUpdates) return
        _isListeningReadUpdates = true

        socket?.on("conversation:read:update") { args ->
            val data = args[0] as JSONObject
            val payload = data.getJSONObject("data")

            val updatedConversationId = payload.optInt("conversationId", -1)
            val lastReadMessageId = payload.optInt("last_read_message_id", -1)
            val readByUserId = payload.optInt("userId", -1)

            viewModelScope.launch {
                val current = _conversations.value
                if (current != null) {
                    val updatedList = current.conversations.map { conv ->
                        if (conv.conversation_id == updatedConversationId) {
                            conv.copy(last_read_message_id = lastReadMessageId)
                        } else conv
                    }
                    _conversations.value = current.copy(conversations = updatedList)
                }

                if (updatedConversationId == currentConversationId &&
                    readByUserId != currentUserId
                ) {
                    _otherUserLastReadMessageId.value = lastReadMessageId
                }
            }
        }
    }

    // Sends images/videos from _mediaList, clears list after, prevents socket duplicate
    fun sendMedia(context: Context, conversationId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.sendMediaMessage(
                    context,
                    conversationId,
                    mediaList.value
                )

                // Register id so socket skips it
                locallyAddedMessageIds.add(response.id)

                _messages.update { current ->
                    current?.copy(messages = current.messages + response)
                }

                _mediaList.value = emptyList() // 👈 clears media list
                notifyScrollToBottom()

            } catch (e: Exception) {
                Log.e("MEDIA_SEND", e.message.toString())
            }
        }
    }

    // Sends audio silently — never touches _mediaList so it never shows in picker
    fun sendAudioSilently(context: Context, conversationId: Int, audioUri: Uri) {
        viewModelScope.launch {
            try {
                val audioMedia = listOf(UploadMedia(audioUri, MediaType.AUDIO))

                val response = repository.sendMediaMessage(
                    context,
                    conversationId,
                    audioMedia
                )

                // Register id so socket skips it
                locallyAddedMessageIds.add(response.id)

                _messages.update { current ->
                    current?.copy(messages = current.messages + response)
                }

                notifyScrollToBottom()

            } catch (e: Exception) {
                Log.e("AUDIO_SEND", e.message.toString())
            }
        }
    }

    private val _convoId = MutableStateFlow<Int?>(null)
    val convoId: StateFlow<Int?> = _convoId

    fun startConversation(userId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.startConversation(userId)
                _convoId.value = response.data.conversation_id
            } catch (e: Exception) {
                Log.e("ChatVM_DEBUG", e.message.toString())
            }
        }
    }

    fun clearConvoId() {
        _convoId.value = null
    }
}