package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.AnalyticsDashboardResponse
import com.example.sociamediaapplication.model.response.ChannelAnalyticsResponse
import com.example.sociamediaapplication.model.response.TopVideosResponse

class AnalyticsRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.analyticsApi

    suspend fun getChannelAnalytics(): ChannelAnalyticsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getChannelAnalytics(token)
    }

    suspend fun getVideoDashboard(): AnalyticsDashboardResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getVideoDashboard(token)
    }

    suspend fun getTopVideos(): TopVideosResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getTopVideos(token)
    }
}