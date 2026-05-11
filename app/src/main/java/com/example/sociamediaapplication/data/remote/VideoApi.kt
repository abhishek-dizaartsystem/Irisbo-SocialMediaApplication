package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface VideoApi {

    @GET("api/videos/categories")
    suspend fun getVideoCategories(
        @Header("Authorization") token: String
    ): VideoCategoryResponse
}