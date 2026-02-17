package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.ReelListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ReelApi {
    @GET("api/reels/all")
    suspend fun getAllReels(): ReelListResponse

    @Multipart
    @POST("api/reels/upload")
    suspend fun uploadReel(
        @Part video: MultipartBody.Part,
        @Part("caption") caption: RequestBody?,
        @Header("Authorization") token: String
    ): BasicResponse
}