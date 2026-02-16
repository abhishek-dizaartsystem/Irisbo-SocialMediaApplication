package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileApi {
    @Multipart
    @PUT("api/profile/profile/profile-image")
    suspend fun uploadProfileImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): BasicResponse

    @Multipart
    @PUT("api/profile/profile/cover-image")
    suspend fun uploadCoverImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    )

    @GET("api/profile/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

}