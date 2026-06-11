package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.request.PostReactionRequest
import com.example.sociamediaapplication.model.request.UpdateReelRequest
import com.example.sociamediaapplication.model.response.BasicResponse2
import com.example.sociamediaapplication.model.response.LikePostResponse
import com.example.sociamediaapplication.model.response.LikeReelResponse
import com.example.sociamediaapplication.model.response.LikeResponse
import com.example.sociamediaapplication.model.response.Reel
import com.example.sociamediaapplication.model.response.ReelListResponse
import com.example.sociamediaapplication.model.response.SaveResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import com.example.sociamediaapplication.model.response.VideoCommentsResponse
import com.example.sociamediaapplication.model.request.AddCommentRequest
import com.example.sociamediaapplication.model.response.VideoReactionRequest

class ReelRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.reelApi

    // ✅ fetch reels
    suspend fun getReels(): List<Reel> {
        return api.getAllReels(token = "Bearer ${tokenManager.getToken()}").data.reels
    }

    // ✅ upload reel (single video)
    suspend fun uploadReel(
        captionText: String?,
        uri: Uri?,
        context: Context
    ) = withContext(Dispatchers.IO) {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        if (uri == null) throw IllegalStateException("Video missing")

        val file = uriToFile(uri, context)

        val mime = context.contentResolver.getType(uri) ?: "video/*"

        val requestFile = file.asRequestBody(
            mime.toMediaTypeOrNull()
        )

        val videoPart = MultipartBody.Part.createFormData(
            name = "video",      // 🔥 must match backend
            filename = file.name,
            body = requestFile
        )

        val caption =
            captionText?.toRequestBody("text/plain".toMediaType())

        api.uploadReel(
            token = "Bearer $token",
            video = videoPart,
            caption = caption
        )
    }

    suspend fun likeReel(reelId: Int): LikeReelResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.toggleLikeReel(
            id = reelId,
            token = "Bearer $token",
            PostReactionRequest("like")
        )
    }

    suspend fun unlikeReel(reelId: Int): LikeReelResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.toggleLikeReel(
            id = reelId,
            token = "Bearer $token",
            PostReactionRequest("dislike")
        )
    }

    suspend fun toggleSave(reelId:Int): SaveResponse{
        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        return api.toggleSaveReel(
            id = reelId,
            token = "Bearer $token"
        )
    }

    suspend fun saveReel(reelId: Int): BasicResponse2{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.saveReel(reelId, token)
    }

    suspend fun unsaveReel(reelId: Int): BasicResponse2{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.unsaveReel(reelId, token)
    }

    suspend fun getMyReels(): List<Reel>{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMyReels(token).data.reels
    }

    suspend fun updateReel(caption: String){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.updateReelCaption(token, UpdateReelRequest(caption))
    }

    suspend fun getUserReels(userId: Int): List<Reel>{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getUserReels(token, userId).data.reels
    }

    suspend fun fetchComments(reelId: Int): VideoCommentsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.getComments(token, reelId)
    }

    suspend fun commentOnReel(reelId: Int, content: String, parentId: Int? = null) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.createComment(token, reelId, AddCommentRequest(content, parentId))
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
