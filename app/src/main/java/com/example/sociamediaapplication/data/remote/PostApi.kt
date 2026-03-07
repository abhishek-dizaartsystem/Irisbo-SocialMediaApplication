package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.GroupPostDetailsResponse
import com.example.sociamediaapplication.model.response.LikeResponse
import com.example.sociamediaapplication.model.response.PostsListResponse
import com.example.sociamediaapplication.model.response.SaveResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PostApi {

    @Multipart
    @POST("api/groups/{group_id}/create")
    suspend fun createGroupPost(
        @Header("Authorization") token: String,
        @Path("group_id") groupId: Int,
        @Part("caption") caption: RequestBody,
        @Part media: List<MultipartBody.Part> = emptyList()
    )

    @GET("api/groups/posts/{post_id}")
    suspend fun getGroupPostDetails(
        @Header("Authorization") token: String,
        @Path("post_id") postId: Int
    ): GroupPostDetailsResponse

    @GET("api/posts/all")
    suspend fun getAllPosts(
        @Header("Authorization") token: String
    ): PostsListResponse

    @Multipart
    @POST("api/posts/create")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Part("caption") caption: RequestBody,
        @Part media: List<MultipartBody.Part>? = emptyList()
    ): BasicResponse

    @POST("api/likePosts/{id}/like")
    suspend fun toggleLikePost(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): LikeResponse

    @POST("api/savePosts/toggle/{id}")
    suspend fun toggleSavePost(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SaveResponse
}
