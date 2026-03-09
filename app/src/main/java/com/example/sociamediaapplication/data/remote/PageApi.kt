package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.PagesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PageApi {

    @GET("api/pages")
    suspend fun fetchPages(
        @Header("Authorization") token: String
    ): PagesResponse

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