package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.PageCategoriesResponse
import com.example.sociamediaapplication.model.response.PageFollowersResponse
import com.example.sociamediaapplication.model.response.PagePostsResponse
import com.example.sociamediaapplication.model.response.PagesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
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
        @Part profile_image: MultipartBody.Part,
        @Part cover_image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("website") website: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody
    )

    @DELETE("api/pages/{pageId}")
    suspend fun deletePage(
        @Header("Authorization") token: String,
        @Path("pageId") pageId: Int
    )

    @POST("api/pages/{pageId}/follow")
    suspend fun followPage(
        @Header("Authorization") token: String,
        @Path("pageId") pageId: Int
    )

    @DELETE("api/pages/{pageId}/unfollow")
    suspend fun unfollowPage(
        @Header("Authorization") token: String,
        @Path("pageId") pageId: Int
    )

    @GET("api/pages/categories")
    suspend fun fetchCategories(
        @Header("Authorization") token: String
    ): PageCategoriesResponse



//    @GET("api/pages/categories/{categoryId}/pages")
//    suspend fun fetchCategoryPages(
//        @Header("Authorization") token: String,
//        @Path("categoryId") categoryId: Int
//    ): CategoryPagesResponse

}