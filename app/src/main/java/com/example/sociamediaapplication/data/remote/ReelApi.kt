package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.LikeResponse
import com.example.sociamediaapplication.model.response.ReelListResponse
import com.example.sociamediaapplication.model.response.SaveResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ReelApi {
    @GET("api/reels/all")
    suspend fun getAllReels(
        @Header("Authorization") token: String
    ): ReelListResponse

    @Multipart
    @POST("api/reels/upload")
    suspend fun uploadReel(
        @Part video: MultipartBody.Part,
        @Part("caption") caption: RequestBody?,
        @Header("Authorization") token: String
    ): BasicResponse

    @POST("api/likeReels/{id}/like")
    suspend fun toggleLikeReel(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): LikeResponse

    @POST("api/saveReels/toggle/{id}")
    suspend fun toggleSaveReel(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SaveResponse
}