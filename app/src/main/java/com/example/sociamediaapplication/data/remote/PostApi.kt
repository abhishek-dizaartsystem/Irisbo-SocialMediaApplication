package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.PostsListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostApi {

    @GET("api/posts/all")
    suspend fun getAllPosts(): PostsListResponse

    @Multipart
    @POST("api/posts/create")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Part("caption") caption: RequestBody,
        @Part media: List<MultipartBody.Part>? = emptyList()
    ): BasicResponse
}
