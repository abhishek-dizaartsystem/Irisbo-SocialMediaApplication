package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.ConversationDetailsResponse
import com.example.sociamediaapplication.model.response.ConversationsResponse
import com.example.sociamediaapplication.model.response.MessagesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChatApi {

    @GET("api/chat")
    suspend fun getConversations(
        @Header("Authorization") token : String
    ): ConversationsResponse

    @GET("api/chat/{conversationId}/messages")
    suspend fun getMessages(
        @Header("Authorization") token : String,
        @Path("conversationId") conversationId: Int
    ): MessagesResponse

    @GET("api/chat/{conversationId}")
    suspend fun getConversationDetails(
        @Header("Authorization") token : String,
        @Path("conversationId") conversationId: Int
    ): ConversationDetailsResponse

}