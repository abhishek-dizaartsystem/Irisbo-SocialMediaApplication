package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.AddProductResponse
import com.example.sociamediaapplication.model.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductApi {
    @GET("api/products/all")
    suspend fun getAllProducts(): List<ProductResponse>

    @Multipart
    @POST("api/products/add")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("category") category: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part?,
    ): AddProductResponse
}