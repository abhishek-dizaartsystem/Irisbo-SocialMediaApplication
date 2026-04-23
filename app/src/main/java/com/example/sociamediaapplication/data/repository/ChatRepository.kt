package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.ConversationDetailsResponse
import com.example.sociamediaapplication.model.response.ConversationsResponse
import com.example.sociamediaapplication.model.response.MessagesResponse

class ChatRepository(
    private val tokenManager: TokenManager
) {
    val api = RetrofitClient.chatApi

    suspend fun getInbox(): ConversationsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getConversations(token)
    }

    suspend fun getMessages(conversationId: Int): MessagesResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMessages(token, conversationId)
    }

    suspend fun getConversationDetails(conversationId: Int): ConversationDetailsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getConversationDetails(token, conversationId)
    }
}