package com.example.sociamediaapplication.data.remote

import android.media.Image
import com.example.sociamediaapplication.model.response.FriendStoriesResponse
import com.example.sociamediaapplication.model.response.MyStoriesResponse
import com.example.sociamediaapplication.model.response.SingleUserStoryResponse
import com.example.sociamediaapplication.model.response.StoryViewerResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StoryApi {
    @GET("api/stories/feed")
    suspend fun getFriendsStories(
        @Header("Authorization") token: String
    ): FriendStoriesResponse

    @Multipart
    @POST("api/stories")
    suspend fun createStory(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    )

    @GET("api/stories/me")
    suspend fun getMyStories(
        @Header("Authorization") token: String
    ): MyStoriesResponse

    @GET("api/stories/user/{userId}")
    suspend fun getSingleUserStories(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): SingleUserStoryResponse

    @POST("api/stories/{storyId}/view")
    suspend fun markStoryViewed(
        @Header("Authorization") token: String,
        @Path("storyId") storyId: Int
    )

    @GET("api/stories/{storyId}/viewers")
    suspend fun getStoryViewers(
        @Header("Authorization") token: String,
        @Path("storyId") storyId: Int
    ): StoryViewerResponse

    @DELETE("api/stories/{storyId}")
    suspend fun deleteStory(
        @Header("Authorization") token: String,
        @Path("storyId") storyId: Int
    )
}