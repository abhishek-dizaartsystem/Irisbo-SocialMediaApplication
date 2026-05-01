package com.example.sociamediaapplication.data.repository

import android.content.Context
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.data.utils.uriToFileWithMime
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.model.UploadMedia
import com.example.sociamediaapplication.model.response.ConversationDetailsResponse
import com.example.sociamediaapplication.model.response.ConversationsResponse
import com.example.sociamediaapplication.model.response.MessageResponse
import com.example.sociamediaapplication.model.response.MessagesResponse
import com.example.sociamediaapplication.model.response.StartConversationResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ChatRepository(
    private val tokenManager: TokenManager
) {
    val api = RetrofitClient.chatApi

    suspend fun getInbox(): ConversationsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getConversations(token)
    }

    suspend fun getMessages(conversationId: Int, page: Int): MessagesResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMessages(token, conversationId, page)
    }

    suspend fun getConversationDetails(conversationId: Int): ConversationDetailsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getConversationDetails(token, conversationId)
    }

    suspend fun markConversationRead(conversationId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.markConversationRead(token, conversationId)
    }

    suspend fun sendMediaMessage(
        context: Context,
        conversationId: Int,
        mediaList: List<UploadMedia>
    ): MessageResponse {

        val token = "Bearer ${tokenManager.getToken()}"

        val parts = mutableListOf<MultipartBody.Part>()

        mediaList.forEach { media ->



            val mime = when (media.mediaType) {
                MediaType.IMAGE -> {
                    context.contentResolver.getType(media.uri) ?: "image/jpeg"
                }
                MediaType.VIDEO -> {
                    context.contentResolver.getType(media.uri) ?: "video/mp4"
                }
                MediaType.AUDIO -> {
                    "audio/mp3"   // ✅ CORRECT MIME
                }
            }

            val file = uriToFileWithMime(media.uri, mime, context) // 👈 same as your event

            val requestFile = file.asRequestBody(
                mime.toMediaTypeOrNull()
            )

            val part = MultipartBody.Part.createFormData(
                "attachments", // 🔥 must match backend upload.array("files")
                file.name,
                requestFile
            )

            parts.add(part)
        }

        // 🔥 message_type (optional but recommended)
        val messageType = when {
            mediaList.any { it.mediaType == MediaType.VIDEO } -> "video"
            mediaList.any { it.mediaType == MediaType.AUDIO } -> "audio"
            else -> "image"
        }.toRequestBody("text/plain".toMediaType())

        return api.sendMediaMessage(
            token,
            conversationId,
            parts,
        )
    }

    suspend fun startConversation(userId: Int): StartConversationResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.startConversation(token, userId)
    }
}