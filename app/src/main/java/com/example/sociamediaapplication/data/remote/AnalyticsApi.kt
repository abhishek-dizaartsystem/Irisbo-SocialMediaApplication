package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.AnalyticsDashboardResponse
import com.example.sociamediaapplication.model.response.ChannelAnalyticsResponse
import com.example.sociamediaapplication.model.response.TopVideosResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AnalyticsApi {
    @GET("api/analytics/me/channel")
    suspend fun getChannelAnalytics(
        @Header("Authorization") token: String,
    ): ChannelAnalyticsResponse

    @GET("api/analytics/me/dashboard")
    suspend fun getVideoDashboard(
        @Header("Authorization") token: String,
    ): AnalyticsDashboardResponse

    @GET("api/analytics/me/videos/top")
    suspend fun getTopVideos(
        @Header("Authorization") token: String,
    ): TopVideosResponse
}