package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.ConversationDetailsResponse
import com.example.sociamediaapplication.model.response.ConversationsResponse
import com.example.sociamediaapplication.model.response.MessageResponse
import com.example.sociamediaapplication.model.response.MessagesResponse
import com.example.sociamediaapplication.model.response.StartConversationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApi {

    @GET("api/chat")
    suspend fun getConversations(
        @Header("Authorization") token : String
    ): ConversationsResponse

    @GET("api/chat/{conversationId}/messages")
    suspend fun getMessages(
        @Header("Authorization") token : String,
        @Path("conversationId") conversationId: Int,
        @Query("page") page: Int
    ): MessagesResponse

    @GET("api/chat/{conversationId}")
    suspend fun getConversationDetails(
        @Header("Authorization") token : String,
        @Path("conversationId") conversationId: Int
    ): ConversationDetailsResponse

    @PUT("api/chat/{conversationId}/read")
    suspend fun markConversationRead(
        @Header("Authorization") token : String,
        @Path("conversationId") conversationId: Int
    )

    @Multipart
    @POST("api/chat/{conversationId}/message")
    suspend fun sendMediaMessage(
        @Header("Authorization") token: String,
        @Path("conversationId") conversationId: Int,
        @Part files: List<MultipartBody.Part>,
    ): MessageResponse


    @POST("api/chat/start/{userId}")
    suspend fun startConversation(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): StartConversationResponse
}