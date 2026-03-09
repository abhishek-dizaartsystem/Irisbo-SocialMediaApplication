package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.PageFollowersResponse
import com.example.sociamediaapplication.model.response.PagePostsResponse
import com.example.sociamediaapplication.model.response.PagesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PageApi {

    @GET("api/pages")
    suspend fun fetchPages(
        @Header("Authorization") token: String
    ): PagesResponse

    @GET("api/pages/{pageId}/followers")
    suspend fun fetchPageFollowers(
        @Header("Authorization") token: String,
        @Path("pageId") pageId: Int
    ): PageFollowersResponse

    @GET("api/pages/{pageId}/posts")
    suspend fun fetchPagePosts(
        @Header("Authorization") token: String,
        @Path("pageId") pageId: Int
    ): PagePostsResponse

    @Multipart
    @POST("api/pages")
    suspend fun createPage(
        @Header("Authorization") token: String,
        @Part cover_image: MultipartBody.Part,
        @Part profile_image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") category: RequestBody
    )
}