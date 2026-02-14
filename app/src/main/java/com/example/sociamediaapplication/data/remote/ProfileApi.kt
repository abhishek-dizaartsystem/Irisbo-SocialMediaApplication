package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {
    @GET("api/profile/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

}