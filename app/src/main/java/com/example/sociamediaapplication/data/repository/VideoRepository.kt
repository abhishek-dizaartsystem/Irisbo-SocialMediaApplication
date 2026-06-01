package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.GetMyVideosResponse
import com.example.sociamediaapplication.model.response.GetVideosResponse
import com.example.sociamediaapplication.model.response.RelatedVideosResponse
import com.example.sociamediaapplication.model.response.SingleVideoResponse
import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import com.example.sociamediaapplication.model.response.VideoCommentsResponse
import com.example.sociamediaapplication.model.response.VideoReactionRequest

class VideoRepository(
    private val tokenManager: TokenManager
) {

    val api = RetrofitClient.videoApi

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

    suspend fun fetchComments(videoId: Int): VideoCommentsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.fetchComments(token, videoId)
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
}