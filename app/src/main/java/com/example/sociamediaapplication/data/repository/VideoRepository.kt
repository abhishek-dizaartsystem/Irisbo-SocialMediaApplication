package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.GetVideosResponse
import com.example.sociamediaapplication.model.response.VideoCategoryResponse

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
}