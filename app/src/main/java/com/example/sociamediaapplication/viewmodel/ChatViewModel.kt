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

    private val _typingUsers = MutableStateFlow<Set<Int>>(emptySet())
    val typingUsers: StateFlow<Set<Int>> = _typingUsers

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

    // Real message IDs added via REST — socket should skip these
    private val locallyAddedMessageIds = mutableSetOf<Int>()

    private val _replyToMessage = MutableStateFlow<MessageResponse?>(null)
    val replyToMessage: StateFlow<MessageResponse?> = _replyToMessage

    fun setReplyMessage(message: MessageResponse) {
        _replyToMessage.value = message
    }

    fun clearReplyMessage() {
        _replyToMessage.value = null
    }

    fun notifyScrollToBottom() { _scrollToBottom.value = true }
    fun consumeScrollToBottom() { _scrollToBottom.value = false }
    fun consumePrependedCount() { _prependedCount.value = 0 }


    fun deleteMessage(messageId: Int) {
        val socket = SocketManager.getSocket()
        val json = JSONObject()
        json.put("messageId", messageId)
        socket?.emit("message:delete", json)
    }

    fun startTyping(conversationId: Int) {
        val socket = SocketManager.getSocket()

        val json = JSONObject()
        json.put("conversationId", conversationId)

        socket?.emit("typing:start", json)
    }

    fun stopTyping(conversationId: Int) {
        val socket = SocketManager.getSocket()

        val json = JSONObject()
        json.put("conversationId", conversationId)

        socket?.emit("typing:stop", json)
    }

    fun observeTyping(conversationId: Int, currentUserId: Int) {

        val socket = SocketManager.getSocket()

        socket?.off("typing:start")
        socket?.off("typing:stop")

        // 🔥 START
        socket?.on("typing:start") { args ->

            val data = args[0] as JSONObject
            val typingData = data.getJSONObject("data")

            val convoId = typingData.optInt("conversationId")
            val userId = typingData.optInt("userId")

            if (convoId != conversationId) return@on
            if (userId == currentUserId) return@on

            viewModelScope.launch {

                val updated = _typingUsers.value.toMutableSet()
                updated.add(userId)

                _typingUsers.value = updated
            }
        }

        // 🔥 STOP
        socket?.on("typing:stop") { args ->

            val data = args[0] as JSONObject
            val typingData = data.getJSONObject("data")

            val convoId = typingData.optInt("conversationId")
            val userId = typingData.optInt("userId")

            if (convoId != conversationId) return@on
            if (userId == currentUserId) return@on

            viewModelScope.launch {

                val updated = _typingUsers.value.toMutableSet()
                updated.remove(userId)

                _typingUsers.value = updated
            }
        }
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
        val socket = SocketManager.getSocket()
        socket?.off("conversation:updated")

        socket?.on("conversation:updated") { args ->
            val data = args[0] as JSONObject
            val convoJson = data.getJSONObject("data")
            val lastMessageJson = convoJson.getJSONObject("last_message")

            viewModelScope.launch {

                val updatedConvoId = convoJson.optInt("conversation_id")
                val lastMessage = lastMessageJson.optString("content")
                val lastMessageAt = lastMessageJson.optString("created_at")

                _conversations.update { current ->

                    val list = current?.conversations ?: return@update current

                    val updatedList = list.map { convo ->
                        if (convo.conversation_id == updatedConvoId) {
                            convo.copy(
                                content = lastMessage,
                                last_message_at = lastMessageAt
                            )
                        } else convo
                    }

                    current.copy(conversations = updatedList)
                }
            }
        }
    }

    fun observePresence() {

        val socket = SocketManager.getSocket()

        socket?.off("presence:sync")
        socket?.off("presence:online")
        socket?.off("presence:offline")

        // 🔹 SYNC (initial full list)
        socket?.on("presence:sync") { args ->

            Log.d("PRESENCE_DEBUG", "SYNC Logs")
            val data = args[0] as JSONObject

            val usersArray = data.optJSONArray("onlineUserIds")

            val onlineSet = mutableSetOf<Int>()

            if (usersArray != null) {
                for (i in 0 until usersArray.length()) {
                    onlineSet.add(usersArray.getInt(i))
                }
            }

            Log.d("PRESENCE_DEBUG", "SYNC → $onlineSet")

            _onlineUsers.value = onlineSet   // ✅ FIXED
            Log.d("PRESENCE_DEBUG", "SYNC → ${_onlineUsers.value}")

        }

        // 🔹 USER ONLINE
        socket?.on("presence:online") { args ->
            Log.d("PRESENCE_DEBUG", "ONLINE Logs")
            val data = args[0] as JSONObject
            val userId = data.optInt("userId")

            Log.d("PRESENCE_DEBUG", "ONLINE1 → ${_onlineUsers.value}")

            val updated = _onlineUsers.value.toMutableSet()

            Log.d("PRESENCE_DEBUG", "Updated2 → $updated")

            updated.add(userId)

            Log.d("PRESENCE_DEBUG", "Updated2 → $updated")

            Log.d("PRESENCE_DEBUG", "ONLINE2 → ${_onlineUsers.value}")

            Log.d("PRESENCE_DEBUG", "ONLINE → $userId")

            _onlineUsers.value = updated   // ✅ FIXED

            Log.d("PRESENCE_DEBUG", "ONLINE3 → ${_onlineUsers.value}")

            Log.d("PRESENCE_DEBUG", "ONLINE4 → ${_onlineUsers.value}")
        }

        // 🔹 USER OFFLINE
        socket?.on("presence:offline") { args ->
            Log.d("PRESENCE_DEBUG", "OFFLINE Logs")
            val data = args[0] as JSONObject
            val userId = data.optInt("userId")

            Log.d("PRESENCE_DEBUG", "OFFLINEon1 → ${_onlineUsers.value}")

            val updated = _onlineUsers.value.toMutableSet()

            Log.d("PRESENCE_DEBUG", "Updated1 → $updated")

            updated.remove(userId)

            Log.d("PRESENCE_DEBUG", "Updated2 → $updated")

            Log.d("PRESENCE_DEBUG", "OFFLINEon2 → ${_onlineUsers.value}")

            Log.d("PRESENCE_DEBUG", "OFFLINE → $userId")

            _onlineUsers.value = updated   // ✅ FIXED
            Log.d("PRESENCE_DEBUG", "OFFLINEon4 → ${_onlineUsers.value}")
        }
    }

    fun observeSocketMessages(conversationId: Int, uid: Int) {
        val socket = SocketManager.getSocket()
        socket?.off("message:new")

        socket?.on("message:new") { args ->
            val data = args[0] as JSONObject
            val messageJson = data.getJSONObject("data")

            Log.d("MESSAGE_DEBUG", messageJson.toString())

            val msgConversationId = messageJson.optInt("conversation_id", -1)
            if (msgConversationId != conversationId) return@on

            val incomingId = messageJson.optInt("id", -1)

            viewModelScope.launch {

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
                    content = if (messageJson.isNull("content")) null else messageJson.optString("content", ""),
                    reply_to_message_id = messageJson.optString("reply_to_message_id"),
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
                    reply_message_id = messageJson.optInt("reply_message_id"),
                    reply_message_content =
                        if (messageJson.isNull("reply_message_content")) null
                        else messageJson.optString("reply_message_content"),
                    reply_message_type = messageJson.optString("reply_message_type"),
                    reply_message_sender_id = messageJson.optInt("reply_message_sender_id"),
                    attachments = parsedAttachments
                )

                _messages.update { current ->

                    if (current == null) return@update current

                    //Prevent duplicate
                    if (current.messages.any { it.id == incomingId }) {
                        return@update current
                    }

                    //REMOVE ONLY TEMP MESSAGE (not all)
                    val cleaned = current.messages.filterNot { temp ->
                        temp.sender_id == -1 && temp.message_type == newMsg.message_type
                    }

                    current.copy(messages = cleaned + newMsg)
                }

                notifyScrollToBottom()
            }
        }
    }

    fun sendMessage(conversationId: Int, text: String) {
        val socket = SocketManager.getSocket()
        val json = JSONObject()

        json.put("conversationId", conversationId)
        json.put("content", text)

        _replyToMessage.value?.let {
            json.put("reply_to_message_id", it.id)
        }

        socket?.emit("message:send", json)

        clearReplyMessage()
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

    fun observeReadUpdates(uid: Int, activeConversationId: Int) {
        val socket = SocketManager.getSocket()
        socket?.off("conversation:read:update")

        socket?.on("conversation:read:update") { args ->
            val data = args[0] as JSONObject
            val json = data.getJSONObject("data")

            val conversationId = json.optInt("conversationId")
            val lastReadMessageId = json.optInt("last_read_message_id")
            val readerId = json.optInt("userId")

            viewModelScope.launch {

                if (conversationId == activeConversationId && readerId != uid) {
                    _otherUserLastReadMessageId.value = lastReadMessageId  // ← this was missing
                }

                // 🔹 update messages (if open chat)
                if (conversationId == activeConversationId) {
                    _messages.update { current ->
                        current?.copy(
                            messages = current.messages.map {
                                if (it.id <= lastReadMessageId) {
                                    it.copy(is_deleted = 0) // or add read flag if you have
                                } else it
                            }
                        )
                    }
                }

                // 🔹 update chat list
                _conversations.update { current ->
                    val list = current?.conversations ?: return@update current

                    val updated = list.map {
                        if (it.conversation_id == conversationId) {
                            it.copy(last_read_message_id = lastReadMessageId)
                        } else it
                    }

                    current.copy(conversations = updated)
                }
            }
        }
    }

    fun sendMedia(context: Context, conversationId: Int, profileId: Int) {

        val tempId = System.currentTimeMillis().toInt()

        Log.d("ChatVM_DEBUG", tempId.toString())

//        val tempMessage = MessageResponse(
//            id = tempId,
//            conversation_id = conversationId,
//            sender_id = -1,
//            message_type = "media",
//            content = null,
//            reply_to_message_id = null,
//            client_temp_id = tempId.toString(),
//            is_edited = 0,
//            edited_at = null,
//            is_deleted = 0,
//            deleted_at = null,
//            created_at = "",
//            updated_at = "",
//            sender_name = "",
//            sender_username = "",
//            sender_profile_image = "",
//            reply_message_id = null,
//            reply_message_content = null,
//            reply_message_type = null,
//            reply_message_sender_id = null,
//            attachments = mediaList.value.map {
//                Attachment(
//                    id = tempId,
//                    message_id = tempId,
//                    file_url = it.uri.toString(),
//                    file_type = it.mediaType.name.lowercase(),
//                    mime_type = "",
//                    original_name = "",
//                    file_size = 0,
//                    created_at = ""
//                )
//            }
//        )

        // ✅ SHOW instantly
//        _messages.update { it?.copy(messages = it.messages + tempMessage) }
//        notifyScrollToBottom()

        val sendingMedia = mediaList.value
        _mediaList.value = emptyList()

        viewModelScope.launch {
            try {
                repository.sendMediaMessage(context, conversationId, sendingMedia)

                // 🔥 ONLY REMOVE TEMP — DO NOT ADD RESPONSE
//                _messages.update {
//                    it?.copy(messages = it.messages.filter { msg -> msg.id != tempId })
//                }

            } catch (e: Exception) {
                _messages.update {
                    it?.copy(messages = it.messages.filter { msg -> msg.id != tempId })
                }
                Log.e("MEDIA_SEND", e.message.toString())
            }
        }
    }

    fun sendAudioSilently(context: Context, conversationId: Int, audioUri: Uri) {

        val tempId = System.currentTimeMillis().toInt()

//        val tempMessage = MessageResponse(
//            id = tempId,
//            conversation_id = conversationId,
//            sender_id = -1,
//            message_type = "audio",
//            content = "",
//            reply_to_message_id = null,
//            client_temp_id = tempId.toString(),
//            is_edited = 0,
//            edited_at = null,
//            is_deleted = 0,
//            deleted_at = null,
//            created_at = "",
//            updated_at = "",
//            sender_name = "",
//            sender_username = "",
//            sender_profile_image = "",
//            reply_message_id = null,
//            reply_message_content = null,
//            reply_message_type = null,
//            reply_message_sender_id = null,
//            attachments = listOf(
//                Attachment(
//                    id = tempId,
//                    message_id = tempId,
//                    file_url = audioUri.toString(),
//                    file_type = "audio",
//                    mime_type = "",
//                    original_name = "",
//                    file_size = 0,
//                    created_at = ""
//                )
//            )
//        )
//
//        _messages.update { it?.copy(messages = it.messages + tempMessage) }
//        notifyScrollToBottom()

        viewModelScope.launch {
            try {
                repository.sendMediaMessage(
                    context,
                    conversationId,
                    listOf(UploadMedia(audioUri, MediaType.AUDIO))
                )

                // 🔥 ONLY REMOVE TEMP
//                _messages.update {
//                    it?.copy(messages = it.messages.filter { msg -> msg.id != tempId })
//                }

            } catch (e: Exception) {
                _messages.update {
                    it?.copy(messages = it.messages.filter { msg -> msg.id != tempId })
                }
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