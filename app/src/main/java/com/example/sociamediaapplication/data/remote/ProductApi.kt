package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.AddReviewRequest
import com.example.sociamediaapplication.model.request.AddToCartRequest
import com.example.sociamediaapplication.model.request.AddToWishlistRequest
import com.example.sociamediaapplication.model.request.ReplyReviewRequest
import com.example.sociamediaapplication.model.request.ReviewReactionTypeRequest
import com.example.sociamediaapplication.model.response.AddProductResponse
import com.example.sociamediaapplication.model.response.AddReviewResponse
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.BasicResponse2
import com.example.sociamediaapplication.model.response.BasicResponse3
import com.example.sociamediaapplication.model.response.CartResponse
import com.example.sociamediaapplication.model.response.CategoryProducts
import com.example.sociamediaapplication.model.response.CheckoutDetailsResponse
import com.example.sociamediaapplication.model.response.EditProductResponse
import com.example.sociamediaapplication.model.response.ProductCategoriesType
import com.example.sociamediaapplication.model.response.ProductDetailsResponse
import com.example.sociamediaapplication.model.response.ReviewsResponse
import com.example.sociamediaapplication.model.response.SearchProductResponse
import com.example.sociamediaapplication.model.response.UserProductsResponse
import com.example.sociamediaapplication.model.response.VendorProductsResponse
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
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    //NEW APIS
    @GET("api/marketplace/products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): UserProductsResponse

    @GET("api/marketplace/my-products")
    suspend fun getAllVendorProducts(
        @Header("Authorization") token: String
    ): VendorProductsResponse

    @GET("api/marketplace/categories")
    suspend fun getProductCategoryTypes(): ProductCategoriesType

    @GET("api/marketplace/products/{id}")
    suspend fun getProductDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ProductDetailsResponse

    @Multipart
    @POST("api/marketplace/products/create")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("category_id") category_id: RequestBody,
        @Part("price") price: RequestBody,
        @Part("discount") discount: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("description") description: RequestBody,
        @Part("specifications") specs: RequestBody,
        @Part images: List<MultipartBody.Part?>,
    ): AddProductResponse


    @DELETE("api/marketplace/products/{id}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): BasicResponse2

    @GET("api/marketplace/cart")
    suspend fun fetchCart(
        @Header("Authorization") token: String
    ): CartResponse

    @POST("api/marketplace/cart/{id}")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Path("id")id: Int,
        @Body request: AddToCartRequest
    ): BasicResponse

    @PUT("api/marketplace/cart/{id}")
    suspend fun updateCartQuantity(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: AddToCartRequest
    ): BasicResponse3

    @DELETE("api/marketplace/cart/{productId}")
    suspend fun deleteCartProduct(
        @Header("Authorization") token: String,
        @Path("productId") productId: String
    ): BasicResponse

    @GET("api/marketplace/wishlist") // Not Tested
    suspend fun fetchWishlist(
        @Header("Authorization") token: String
    ): WishlistResponse

    @DELETE("api/marketplace/wishlist/{product_id}") // Not Tested
    suspend fun removeFromWishlist(
        @Header("Authorization") token: String,
        @Path("product_id") productId: String
    ): BasicResponse

    @POST("api/marketplace/wishlist/{product_id}") // Not Tested
    suspend fun toggleWishList(
        @Header("Authorization")token: String,
        @Path("product_id") product_id: Int
    ): BasicResponse

    @GET("api/marketplace/reviews/product/{id}")
    suspend fun fetchReviews(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ReviewsResponse

    @POST("api/marketplace/reviews/{id}") //Not Tested
    suspend fun addReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: AddReviewRequest
    ): AddReviewResponse

    @DELETE("api/marketplace/reviews/delete/{id}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): AddReviewResponse

    @GET("api/marketplace/category/{id}")
    suspend fun fetchCategoryProducts(
        @Path("id") id: Int
    ): CategoryProducts

    @GET("api/marketplace/products")
    suspend fun searchProducts(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): SearchProductResponse












    //OLD APIS


//    @GET("api/products/{id}/reviews")
//    suspend fun fetchReviews(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int
//    ): ReviewsResponse

//    @GET("api/products/category/{id}")
//    suspend fun fetchCategoryProducts(
//        @Path("id") id: Int
//    ): UserProductsResponse

//    @GET("api/products/search")
//    suspend fun searchProducts(
//        @Query("query") query: String,
//        @Query("page") page: Int,
//        @Query("limit") limit: Int
//    ): SearchProductResponse


    @POST("api/products/{id}/react")
    suspend fun reactToReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: ReviewReactionTypeRequest
    ): BasicResponse2

    @POST("api/products/{id}/reply/react")
    suspend fun reactToReviewReply(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: ReviewReactionTypeRequest
    ): BasicResponse2

    @POST("api/marketplace/reviews/{id}/reply")
    suspend fun addReviewReply(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: ReplyReviewRequest
    ): BasicResponse2

//    @POST("api/products/{id}/review")
//    suspend fun addReview(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int,
//        @Body request: AddReviewRequest
//    ): AddReviewResponse

//    @GET("api/products/categories")
//    suspend fun getProductCategoryTypes(): ProductCategoriesType

//    @GET("api/products/{id}")
//    suspend fun getProductDetails(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int
//    ): ProductDetailsResponse

//    @GET("api/products/vendor/all")
//    suspend fun getAllVendorProducts(
//        @Header("Authorization") token: String
//    ): VendorProductsResponse

//    @GET("api/products/user/all")
//    suspend fun getAllUserProducts(
//        @Header("Authorization") token: String
//    ): UserProductsResponse

//    @Multipart
//    @POST("api/products/add")
//    suspend fun addProduct(
//        @Header("Authorization") token: String,
//        @Part("name") name: RequestBody,
//        @Part("category_id") category_id: RequestBody,
//        @Part("price") price: RequestBody,
//        @Part("discount") discount: RequestBody,
//        @Part("stock") stock: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("specifications") specs: RequestBody,
//        @Part images: List<MultipartBody.Part?>,
//    ): AddProductResponse

    @Multipart
    @PATCH("api/products/edit/{id}")
    suspend fun editProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("category_id") category_id: RequestBody,
        @Part("price") price: RequestBody,
        @Part("discount") discount: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("description") description: RequestBody,
        @Part("specifications") specs: RequestBody,
        @Part images: List<MultipartBody.Part?>,
    ): EditProductResponse

//    @POST("api/cart/add")
//    suspend fun addToCart(
//        @Header("Authorization") token: String,
//        @Body request: AddToCartRequest
//    ): BasicResponse

//    @GET("api/cart/all")
//    suspend fun fetchCart(
//        @Header("Authorization") token: String
//    ): List<CartResponse>

//    @DELETE("api/cart/remove/{productId}")
//    suspend fun deleteCartProduct(
//        @Header("Authorization") token: String,
//        @Path("productId") productId: String
//    ): BasicResponse


//    @GET("api/wishlist/all")
//    suspend fun fetchWishlist(
//        @Header("Authorization") token: String
//    ): List<WishlistResponse>

    @POST("api/wishlist/add")
    suspend fun addToWishlist(
        @Header("Authorization") token: String,
        @Body request: AddToWishlistRequest
    ): BasicResponse

//    @DELETE("api/wishlist/remove/{product_id}")
//    suspend fun removeFromWishlist(
//        @Header("Authorization") token: String,
//        @Path("product_id") productId: String
//    ): BasicResponse

    @GET("api/checkout/details")
    suspend fun fetchCheckoutDetails(
        @Header("Authorization") token: String
    ): CheckoutDetailsResponse

//    @DELETE("api/products/{id}")
//    suspend fun deleteProduct(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int
//    ): BasicResponse2

}