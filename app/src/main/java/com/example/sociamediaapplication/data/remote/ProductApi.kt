package com.example.sociamediaapplication.data.remote

import androidx.room.Delete
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.model.request.AddToCartRequest
import com.example.sociamediaapplication.model.request.AddToWishlistRequest
import com.example.sociamediaapplication.model.response.AddProductResponse
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.CartResponse
import com.example.sociamediaapplication.model.response.CheckoutDetailsResponse
import com.example.sociamediaapplication.model.response.ProductResponse
import com.example.sociamediaapplication.model.response.UserProductsResponse
import com.example.sociamediaapplication.model.response.WishlistResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProductApi {
    @GET("api/products/vendor/all")
    suspend fun getAllVendorProducts(
        @Header("Authorization") token: String
    ): List<ProductResponse>

    @GET("api/products/user/all")
    suspend fun getAllUserProducts(
        @Header("Authorization") token: String
    ): UserProductsResponse
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

    @POST("api/cart/add")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body request: AddToCartRequest
    ): BasicResponse

    @GET("api/cart/all")
    suspend fun fetchCart(
        @Header("Authorization") token: String
    ): List<CartResponse>

    @DELETE("api/cart/remove/{productId}")
    suspend fun deleteCartProduct(
        @Header("Authorization") token: String,
        @Path("productId") productId: String
    ): BasicResponse


    @GET("api/wishlist/all")
    suspend fun fetchWishlist(
        @Header("Authorization") token: String
    ): List<WishlistResponse>

    @POST("api/wishlist/add")
    suspend fun addToWishlist(
        @Header("Authorization") token: String,
        @Body request: AddToWishlistRequest
    ): BasicResponse

    @DELETE("api/wishlist/remove/{product_id}")
    suspend fun removeFromWishlist(
        @Header("Authorization") token: String,
        @Path("product_id") productId: String
    ): BasicResponse

    @GET("api/checkout/details")
    suspend fun fetchCheckoutDetails(
        @Header("Authorization") token: String
    ): CheckoutDetailsResponse
}