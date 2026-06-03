package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.local.database.DatabaseProvider
import com.example.sociamediaapplication.data.local.database.DownloadedVideoEntity
import com.example.sociamediaapplication.data.local.downloader.VideoDownloader
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.request.AddCommentRequest
import com.example.sociamediaapplication.model.response.GetMyVideosResponse
import com.example.sociamediaapplication.model.response.GetVideosResponse
import com.example.sociamediaapplication.model.response.RelatedVideosResponse
import com.example.sociamediaapplication.model.response.SingleVideoResponse
import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import com.example.sociamediaapplication.model.response.VideoCommentsResponse
import com.example.sociamediaapplication.model.response.VideoReactionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class VideoRepository(
    private val tokenManager: TokenManager,
    private val context: Context
){

    val api = RetrofitClient.videoApi

    private val downloader = VideoDownloader(context)

    private val dao =
        DatabaseProvider
            .getDatabase(context)
            .downloadedVideoDao()

    suspend fun downloadVideo(
        videoId: Int,
        title: String,
        thumbnailUrl: String,
        videoUrl: String
    ) {

        val localPath =
            downloader.downloadVideo(
                videoId.toString(),
                videoUrl
            )

        dao.insert(
            DownloadedVideoEntity(
                videoId = videoId,
                title = title,
                thumbnailUrl = thumbnailUrl,
                videoUrl = videoUrl,
                localPath = localPath
            )
        )
    }

    suspend fun getDownloadedVideos() = dao.getAllVideos()

    suspend fun isVideoDownloaded(
        videoId: Int
    ): Boolean {

        return dao.getVideo(videoId) != null
    }

    suspend fun deleteDownloadedVideo(
        videoId: Int
    ) {

        val video =
            dao.getVideo(videoId)

        video?.let {

            File(it.localPath).delete()

            dao.delete(it)
        }
    }

    suspend fun getVideoCategories(): VideoCategoryResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getVideoCategories(token)
    }

    suspend fun searchVideos(value: String): GetVideosResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.searchVideos(token, value)
    }

    suspend fun getMyVideos(): GetMyVideosResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMyVideos(token)
    }

    suspend fun getVideosByCategory(categoryId: Int): GetVideosResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getVideosByCategory(token, categoryId)
    }

    suspend fun getAllVideos(): GetVideosResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getAllVideos(token)
    }

    suspend fun getVideo(videoId: Int): SingleVideoResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getVideo(token, videoId)
    }

    suspend fun getRelatedVideos(videoId: Int): RelatedVideosResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getRelatedVideos(token, videoId)
    }

    suspend fun likeVideo(videoId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.reactToVideo(token, videoId, VideoReactionRequest("like"))
    }

    suspend fun dislikeVideo(videoId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.reactToVideo(token, videoId, VideoReactionRequest("dislike"))
    }

    suspend fun removeVideoReaction(videoId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.removeVideoReaction(token, videoId)
    }

    suspend fun saveVideo(videoId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.saveVideo(token, videoId)
    }

    suspend fun unsaveVideo(videoId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.unsaveVideo(token, videoId)
    }

    suspend fun subscribeToCreator(creatorId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.subscribeToCreator(token, creatorId)
    }

    suspend fun unsubscribeFromCreator(creatorId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.unsubscribeFromCreator(token, creatorId)
    }

    suspend fun fetchComments(videoId: Int, sort: String? = null): VideoCommentsResponse{
        val token = "Bearer ${tokenManager.getToken()}"



        return api.fetchComments(
            token,
            videoId,
            if(sort == null || sort == "asc") "asc"
            else "desc"
        )
    }

    suspend fun likeComment(commentId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        api.reactToComment(token, commentId, VideoReactionRequest("like"))
    }

    suspend fun dislikeComment(commentId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        api.reactToComment(token, commentId, VideoReactionRequest("dislike"))
    }

    suspend fun removeCommentReaction(commentId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        api.removeCommentReaction(token, commentId)
    }

    suspend fun commentOnVideo(videoId: Int, content: String, parentId: Int? = null){
        val token = "Bearer ${tokenManager.getToken()}"

        api.commentOnVideo(token, videoId, AddCommentRequest(content, parentId))
    }

    suspend fun uploadVideo(
        title: String,
        description: String,
        categoryId: Int,
        videoUri: Uri?,
        thumbnailUri: Uri?,
        context: Context
    ) = withContext(Dispatchers.IO) {

        val token =
            tokenManager.getToken()
                ?: throw Exception("No token")

        require(videoUri != null)
        require(thumbnailUri != null)

        val videoFile =
            uriToFile(videoUri, context)

        val thumbnailFile =
            uriToFile(thumbnailUri, context)

        val videoPart =
            MultipartBody.Part.createFormData(
                "video",
                videoFile.name,
                videoFile.asRequestBody(
                    (
                            context.contentResolver.getType(videoUri)
                                ?: "video/mp4"
                            ).toMediaTypeOrNull()
                )
            )

        val thumbnailPart =
            MultipartBody.Part.createFormData(
                "thumbnail",
                thumbnailFile.name,
                thumbnailFile.asRequestBody(
                    (
                            context.contentResolver.getType(thumbnailUri)
                                ?: "image/jpeg"
                            ).toMediaTypeOrNull()
                )
            )

        api.uploadVideo(
            token = "Bearer $token",

            video = videoPart,

            thumbnail = thumbnailPart,

            title = title.toRequestBody(
                "text/plain".toMediaType()
            ),

            description = description.toRequestBody(
                "text/plain".toMediaType()
            ),

            categoryId = categoryId.toString()
                .toRequestBody(
                    "text/plain".toMediaType()
                )
        )
    }
}