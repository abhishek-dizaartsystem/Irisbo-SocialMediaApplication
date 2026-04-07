package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.PostReactionRequest
import com.example.sociamediaapplication.model.request.UpdateReelRequest
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.BasicResponse2
import com.example.sociamediaapplication.model.response.LikePostResponse
import com.example.sociamediaapplication.model.response.LikeReelResponse
import com.example.sociamediaapplication.model.response.LikeResponse
import com.example.sociamediaapplication.model.response.ReelListResponse
import com.example.sociamediaapplication.model.response.SaveResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ReelApi {
    @GET("api/reels/all")
    suspend fun getAllReels(
        @Header("Authorization") token: String
    ): ReelListResponse

    @GET("api/reels/my")
    suspend fun getMyReels(
        @Header("Authorization") token: String
    ): ReelListResponse

    @Multipart
    @POST("api/reels/create")
    suspend fun uploadReel(
        @Part video: MultipartBody.Part,
        @Part("caption") caption: RequestBody?,
        @Header("Authorization") token: String
    ): BasicResponse

    @POST("api/reels/{id}/react")
    suspend fun toggleLikeReel(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: PostReactionRequest
    ): LikeReelResponse

    @POST("api/reels/{id}/save")
    suspend fun saveReel(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): BasicResponse2

    @DELETE("api/reels/{id}/save")
    suspend fun unsaveReel(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): BasicResponse2

    @POST("api/saveReels/toggle/{id}")
    suspend fun toggleSaveReel(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SaveResponse

    @PUT("api/reels/{reelId}")
    suspend fun updateReelCaption(
        @Header("Authorization") token: String,
        @Body request: UpdateReelRequest
    )
}