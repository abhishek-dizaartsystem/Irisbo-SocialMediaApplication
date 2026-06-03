package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.AddCommentRequest
import com.example.sociamediaapplication.model.response.GetMyVideosResponse
import com.example.sociamediaapplication.model.response.GetVideosResponse
import com.example.sociamediaapplication.model.response.RelatedVideosResponse
import com.example.sociamediaapplication.model.response.SingleVideoResponse
import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import com.example.sociamediaapplication.model.response.VideoCommentsResponse
import com.example.sociamediaapplication.model.response.VideoReactionRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @POST("api/videos/creator/{creatorId}/subscribe")
    suspend fun subscribeToCreator(
        @Header("Authorization") token: String,
        @Path("creatorId") videoId: Int
    )

    @DELETE("api/videos/creator/{creatorId}/subscribe")
    suspend fun unsubscribeFromCreator(
        @Header("Authorization") token: String,
        @Path("creatorId") videoId: Int
    )

    @GET("api/videos/{videoId}/comments")
    suspend fun fetchComments(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int,
        @Query("sort") sort: String
    ): VideoCommentsResponse

    @POST("api/comments/{commentId}/react")
    suspend fun reactToComment(
        @Header("Authorization") token: String,
        @Path("commentId") commentId: Int,
        @Body request: VideoReactionRequest
    )

    @DELETE("api/comments/{commentId}/react")
    suspend fun removeCommentReaction(
        @Header("Authorization") token: String,
        @Path("commentId") videoId: Int
    )

    @POST("api/videos/{videoId}/comments")
    suspend fun commentOnVideo(
        @Header("Authorization") token: String,
        @Path("videoId") videoId: Int,
        @Body request: AddCommentRequest
    )

    @Multipart
    @POST("api/videos")
    suspend fun uploadVideo(

        @Header("Authorization")
        token: String,

        @Part video: MultipartBody.Part,

        @Part thumbnail: MultipartBody.Part,

        @Part("title")
        title: RequestBody,

        @Part("description")
        description: RequestBody,

        @Part("category_id")
        categoryId: RequestBody
    )
}