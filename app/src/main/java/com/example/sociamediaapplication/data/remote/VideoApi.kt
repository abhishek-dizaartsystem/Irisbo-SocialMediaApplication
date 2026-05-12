package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.GetMyVideosResponse
import com.example.sociamediaapplication.model.response.GetVideosResponse
import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {

    @GET("api/videos/categories")
    suspend fun getVideoCategories(
        @Header("Authorization") token: String
    ): VideoCategoryResponse

    @GET("api/videos/search")
    suspend fun searchVideos(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): GetVideosResponse

    @GET("api/videos/me/videos")
    suspend fun getMyVideos(
        @Header("Authorization") token: String,
    ): GetMyVideosResponse

    @GET("api/videos/category/{categoryId}")
    suspend fun getVideosByCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Int
    ): GetVideosResponse
}