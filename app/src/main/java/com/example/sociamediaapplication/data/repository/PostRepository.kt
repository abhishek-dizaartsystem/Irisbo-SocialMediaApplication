package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.response.LikeResponse
import com.example.sociamediaapplication.model.response.PostResponse
import com.example.sociamediaapplication.model.response.SaveResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PostRepository(
    private val tokenManager: TokenManager
) {
    private val api = RetrofitClient.postApi

    suspend fun getPosts(): List<PostResponse> {
        val token = tokenManager.getToken()
        println("TOKEN DEBUG: $token")

        val response = api.getAllPosts(
            token = "Bearer $token"
        )

        println("POSTS DEBUG: ${response.posts.size}")

        return response.posts
    }

    suspend fun toggleLike(postId: Int): LikeResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.toggleLikePost(
            id = postId,
            token = "Bearer $token"
        )
    }

    suspend fun toggleSave(postId: Int): SaveResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.toggleSavePost(
            id = postId,
            token = "Bearer $token"
        )
    }

    suspend fun createPost(
        captionText: String,
        uris: List<Uri>?,   // 🔥 multiple media supported
        context: Context
    ) {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        val caption = captionText.toRequestBody("text/plain".toMediaType())

        // 🔥 convert ALL uris to multipart parts
        val mediaParts = uris?.map { uri ->

            val file = uriToFile(uri, context)

            val mime = context.contentResolver.getType(uri) ?: "image/*"

            val requestFile = file.asRequestBody(
                mime.toMediaTypeOrNull()
            )

            MultipartBody.Part.createFormData(
                name = "media",     // must match backend field
                filename = file.name,
                body = requestFile
            )
        } ?: emptyList()

        api.createPost(
            token = "Bearer $token",
            caption = caption,
            media = mediaParts
        )
    }
}
