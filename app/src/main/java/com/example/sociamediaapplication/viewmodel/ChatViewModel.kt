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

    private var _isListening = false

    fun deleteMessage(messageId: Int) {
        val socket = SocketManager.getSocket()

        val json = JSONObject()
        json.put("messageId", messageId)

        socket?.emit("message:delete", json)
    }

    fun observeDeleteMessages() {
        val socket = SocketManager.getSocket()

        socket?.on("message:deleted") { args ->

            val data = args[0] as JSONObject
            val deletedMessage = data.getJSONObject("data")

            val deletedId = deletedMessage.optInt("message_id")

            viewModelScope.launch {
                val current = _messages.value ?: return@launch

                val updatedList = current.messages.filter {
                    it.id != deletedId
                }

                _messages.value = current.copy(
                    messages = updatedList
                )
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

    fun observeSocketMessages(conversationId: Int) {
        if (_isListening) return
        _isListening = true

        val socket = SocketManager.getSocket()

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

    fun fetchMessages(conversationId: Int){
        viewModelScope.launch {
            try {
                _messages.value = repository.getMessages(conversationId)
            }catch (e: Exception){
                Log.e("Chat_VM", e.message.toString())
            }
        }
    }

    fun fetchConversationDetails(conversationId: Int){
        viewModelScope.launch {
            try {
                _conversationDetails.value = repository.getConversationDetails(conversationId)
            }catch (e: Exception){
                Log.e("Chat_VM", e.message.toString())
            }
        }
    }

}