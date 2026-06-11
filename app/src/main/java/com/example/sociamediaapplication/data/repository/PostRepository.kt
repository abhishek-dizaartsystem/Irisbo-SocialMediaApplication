package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.request.PostReactionRequest
import com.example.sociamediaapplication.model.response.BasicResponse2
import com.example.sociamediaapplication.model.response.GlobalPostsResponse
import com.example.sociamediaapplication.model.response.GroupPostDetailsResponse
import com.example.sociamediaapplication.model.response.LikePostResponse
import com.example.sociamediaapplication.model.response.LikeResponse
import com.example.sociamediaapplication.model.response.PostResponse
import com.example.sociamediaapplication.model.response.SaveResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import com.example.sociamediaapplication.model.response.VideoCommentsResponse
import com.example.sociamediaapplication.model.request.AddCommentRequest
import com.example.sociamediaapplication.model.response.VideoReactionRequest

class PostRepository(
    private val tokenManager: TokenManager
) {
    private val api = RetrofitClient.postApi



    suspend fun getPosts(id: Int): List<PostResponse> {
        val token = tokenManager.getToken()
        Log.d("TOKEN DEBUG" ,"$token")

        val response = api.getAllPosts(
            token = "Bearer $token",
            id
        )

        Log.d("POST DEBUG" ,"${response.size}")

        return response
    }

    suspend fun getGlobalPosts(): GlobalPostsResponse {
        val token = tokenManager.getToken()
        return api.getAllPostsGloabal("Bearer $token")
    }


//    suspend fun toggleLike(postId: Int): LikePostResponse {
//
//        val token = tokenManager.getToken()
//            ?: throw IllegalStateException("No token")
//
//        return api.toggleLikePost(
//            id = postId,
//            token = "Bearer $token",
//        )
//    }

    suspend fun likePost(postId: Int): LikePostResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.toggleLikePost(
            id = postId,
            token = "Bearer $token",
            request = PostReactionRequest("like")
        )
    }
    suspend fun unlikePost(postId: Int): LikePostResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.toggleLikePost(
            id = postId,
            token = "Bearer $token",
            request = PostReactionRequest("dislike")
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

    suspend fun savePost(postId: Int): BasicResponse2{
        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.savePost(
            postId,
            "Bearer $token"
        )
    }

    suspend fun unsavePost(postId: Int): BasicResponse2{
        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.unsavePost(
            postId,
            "Bearer $token"
        )
    }

    suspend fun createGroupPost(
        groupId: Int,
        captionText: String,
        uris: List<Uri>? = null,
        context: Context
    ){
        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

//        Log.d("TOKEN_DEBUG", token)

        val caption = captionText.toRequestBody("text/plain".toMediaType())

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

        api.createGroupPost(
            "Bearer $token",
            groupId,
            caption,
            media = mediaParts
        )
    }

    suspend fun getGroupPostDetails(
        postId: Int
    ): GroupPostDetailsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getGroupPostDetails(token, postId)
    }

    suspend fun createPost(
        captionText: String,
        uris: List<Uri>?,   // 🔥 multiple media supported
        context: Context
    ) {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

//        Log.d("TOKEN_DEBUG", token)

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

    suspend fun fetchComments(postId: Int): VideoCommentsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.getComments(token, postId)
    }

    suspend fun commentOnPost(postId: Int, content: String, parentId: Int? = null) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.createComment(token, postId, AddCommentRequest(content, parentId))
    }

    suspend fun likeComment(commentId: Int) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.reactToComment(token, commentId, VideoReactionRequest("like"))
    }

    suspend fun dislikeComment(commentId: Int) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.reactToComment(token, commentId, VideoReactionRequest("dislike"))
    }

    suspend fun removeCommentReaction(commentId: Int) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.removeCommentReaction(token, commentId)
    }
}
