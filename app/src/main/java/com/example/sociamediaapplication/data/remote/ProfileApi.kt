package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileApi {
    @Multipart
    @PUT("api/users/profile/profile-image")
    suspend fun uploadProfileImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): BasicResponse

    @Multipart
    @PUT("api/users/profile/cover-image")
    suspend fun uploadCoverImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    )

    @GET("api/users/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @Multipart
    @PUT("api/users/edit")
    suspend fun editProfile(
        @Header("Authorization") token: String,

        @Part("name") name: RequestBody?,
        @Part("username") username: RequestBody?,
        @Part("bio") bio: RequestBody?,
        @Part("work") work: RequestBody?,
        @Part("education") education: RequestBody?,

        @Part profile_img: MultipartBody.Part?,
        @Part cover_img: MultipartBody.Part?
    ): BasicResponse

}