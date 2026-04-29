package com.example.sociamediaapplication.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.preferences.SocketManager
import com.example.sociamediaapplication.data.repository.ChatRepository
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.model.UploadMedia
import com.example.sociamediaapplication.model.response.Conversation
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
): ViewModel() {

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


    fun notifyScrollToBottom() {
        _scrollToBottom.value = true
    }

    fun consumeScrollToBottom() {
        _scrollToBottom.value = false
    }

    private val _prependedCount = MutableStateFlow(0)
    val prependedCount: StateFlow<Int> = _prependedCount

    fun consumePrependedCount() {
        _prependedCount.value = 0
    }

    fun deleteMessage(messageId: Int) {
        val socket = SocketManager.getSocket()

        val json = JSONObject()
        json.put("messageId", messageId)

        socket?.emit("message:delete", json)
    }

//    fun observeDeleteMessages() {
//        val socket = SocketManager.getSocket()
//
//        socket?.on("message:deleted") { args ->
//
//            val data = args[0] as JSONObject
//            val deletedMessage = data.getJSONObject("data")
//
//            val deletedId = deletedMessage.optInt("message_id")
//
//            viewModelScope.launch {
//                val current = _messages.value ?: return@launch
//
//                val updatedList = current.messages.filter {
//                    it.id != deletedId
//                }
//
//                _messages.value = current.copy(
//                    messages = updatedList
//                )
//            }
//        }
//    }

    fun observeDeleteMessages(conversationId: Int) {

        val socket = SocketManager.getSocket()

        // 🔥 remove old listener (CRITICAL)
        socket?.off("message:deleted")

        socket?.on("message:deleted") { args ->

            val data = args[0] as JSONObject
            val deletedMessage = data.getJSONObject("data")

            val deletedId = deletedMessage.optInt("messageId")
            val msgConversationId = deletedMessage.optInt("conversationId")

            viewModelScope.launch {

                // 🔥 update ChatScreen messages
                val currentMessages = _messages.value
                if (currentMessages != null && msgConversationId == conversationId) {

                    val updatedList = currentMessages.messages.filter {
                        it.id != deletedId
                    }

                    _messages.value = currentMessages.copy(
                        messages = updatedList
                    )
                }

                // 🔥 update ChatsScreen (IMPORTANT)
                val currentConversations = _conversations.value
                if (currentConversations != null) {

                    val updatedConvs = currentConversations.conversations.map { conv ->

                        if (conv.conversation_id == msgConversationId &&
                            conv.last_message_id == deletedId
                        ) {
                            // 🔥 last message was deleted → clear preview
                            conv.copy(
                                content = "Message deleted",
                                last_message_id = null
                            )
                        } else conv
                    }

                    _conversations.value = currentConversations.copy(
                        conversations = updatedConvs
                    )
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

                        // 🔥 KEY LOGIC
                        val shouldMarkUnread = senderId != uid

                        conv.copy(
                            last_message_id = lastMsg?.optInt("id") ?: conv.last_message_id,
                            content = lastMsg?.optString("content") ?: conv.content,
                            last_message_at = lastMsg?.optString("created_at") ?: conv.last_message_at,
                            last_sender_id = senderId,

                            // 🔥 THIS FIXES YOUR ISSUE
                            last_read_message_id = if (shouldMarkUnread) {
                                conv.last_read_message_id   // keep old → unread
                            } else {
                                lastMsg?.optInt("id")       // mark read instantly
                            }
                        )

                    } else conv
                }

                val reordered = updatedList.sortedByDescending { it.last_message_at }

                _conversations.value = current.copy(conversations = reordered)
            }
        }
    }

    fun observePresence() {
        val socket = SocketManager.getSocket()

        socket?.on("presence:online") { args ->
            val data = args[0] as JSONObject
            val userId = data.optInt("userId")

            _onlineUsers.value = _onlineUsers.value + userId
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

        // 🔥 REMOVE previous listener
        socket?.off("message:new")

        socket?.on("message:new") { args ->

            val data = args[0] as JSONObject
            val messageJson = data.getJSONObject("data")

            // 🧪 DEBUG (keep this for now)
            Log.d("SOCKET_DEBUG", messageJson.toString())

            val msgConversationId = messageJson.optInt("conversation_id", -1)

            if (msgConversationId != conversationId) return@on

            viewModelScope.launch {

                val current = _messages.value ?: return@launch

                val newMsg = MessageResponse(
                    id = messageJson.optInt("id", -1),  // safe
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
                    attachments = emptyList()
                )

                _messages.value = current.copy(
                    messages = current.messages + newMsg
                )

                notifyScrollToBottom()
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

    fun addImage(uri: Uri){
        _mediaList.update { current->
            current + UploadMedia(uri, MediaType.IMAGE)
        }
    }

    fun addVideo(uri: Uri){
        _mediaList.update { current->
            current + UploadMedia(uri, MediaType.VIDEO)
        }
    }

    fun fetchConversations(){
        viewModelScope.launch {
            try {
                _conversations.value = repository.getInbox()

                Log.d("ChatVM_Debug", _conversations.value.toString())
            }catch (e: Exception){
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
                Log.d("LoadMore_DEBUG", currentPage.toString())
                Log.d("LoadMore_DEBUG", conversationId.toString())
                val response = repository.getMessages(conversationId, currentPage)

                val current = _messages.value ?: return@launch

                // 🔥 IMPORTANT: prepend older messages
                val combined = response.messages + current.messages

                val unique = combined.distinctBy { it.id }

                _messages.value = current.copy(
                    messages = unique
                )

                val addedCount = unique.size - current.messages.size
                if (addedCount > 0) {
                    _prependedCount.value = addedCount
                }

                // optional: stop if no more pages
                if (response.messages.isEmpty()) {
                    hasMore = false
                }

            } catch (e: Exception) {
                currentPage--  // rollback
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

                // Seed initial read state so ticks are correct on open
                response.data.last_read_message_id.let {
                    _otherUserLastReadMessageId.value = it
                }

            } catch (e: Exception) {
                Log.e("Chat_VM", e.message.toString())
            }
        }
    }

    fun markConversationRead(conversationId: Int){
        viewModelScope.launch {
            try {
                repository.markConversationRead(conversationId)
            } catch (e: Exception){
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
        if (currentUserId == 0) return // don't register until profile is loaded

        // Re-register if already listening to pick up correct userId
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
                // Always update conversations list for ChatsScreen
                val current = _conversations.value
                if (current != null) {
                    val updatedList = current.conversations.map { conv ->
                        if (conv.conversation_id == updatedConversationId) {
                            conv.copy(last_read_message_id = lastReadMessageId)
                        } else conv
                    }
                    _conversations.value = current.copy(conversations = updatedList)
                }

                // Only update tick state if:
                // 1. It's the current open conversation
                // 2. The reader is NOT the current user (i.e. the other person read it)
                if (updatedConversationId == currentConversationId &&
                    readByUserId != currentUserId
                ) {
                    _otherUserLastReadMessageId.value = lastReadMessageId
                }
            }
        }
    }

}