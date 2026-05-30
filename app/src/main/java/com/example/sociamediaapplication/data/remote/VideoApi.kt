package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.GetMyVideosResponse
import com.example.sociamediaapplication.model.response.GetVideosResponse
import com.example.sociamediaapplication.model.response.RelatedVideosResponse
import com.example.sociamediaapplication.model.response.SingleVideoResponse
import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import com.example.sociamediaapplication.model.response.VideoReactionRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

    @GET("api/videos")
    suspend fun getAllVideos(
        @Header("Authorization") token: String,
    ): GetVideosResponse

    @GET("api/videos/{videoId}")
    suspend fun getVideo(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int
    ): SingleVideoResponse

    @GET("api/videos/{videoId}/related")
    suspend fun getRelatedVideos(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int
    ): RelatedVideosResponse

    @POST("api/videos/{videoId}/react")
    suspend fun reactToVideo(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int,
        @Body request: VideoReactionRequest
    )

    @DELETE("api/videos/{videoId}/react")
    suspend fun removeVideoReaction(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int
    )

    @POST("api/videos/{videoId}/save")
    suspend fun saveVideo(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int
    )

    @DELETE("api/videos/{videoId}/save")
    suspend fun unsaveVideo(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int
    )
}