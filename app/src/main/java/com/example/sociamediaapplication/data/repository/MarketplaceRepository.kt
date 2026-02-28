package com.example.sociamediaapplication.data.repository

import android.util.Log
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.AddReviewRequest
import com.example.sociamediaapplication.model.request.AddToCartRequest
import com.example.sociamediaapplication.model.request.AddToWishlistRequest
import com.example.sociamediaapplication.model.request.ReplyReviewRequest
import com.example.sociamediaapplication.model.request.ReviewReactionTypeRequest
import com.example.sociamediaapplication.model.response.AddProductResponse
import com.example.sociamediaapplication.model.response.AddReviewResponse
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.BasicResponse2
import com.example.sociamediaapplication.model.response.CartResponse
import com.example.sociamediaapplication.model.response.CategoryProductsResponse
import com.example.sociamediaapplication.model.response.CheckoutDetailsResponse
import com.example.sociamediaapplication.model.response.EditProductResponse
import com.example.sociamediaapplication.model.response.ReviewsResponse
import com.example.sociamediaapplication.model.response.ProductCategoriesType
import com.example.sociamediaapplication.model.response.ProductDetailsResponse
import com.example.sociamediaapplication.model.response.UserProductsResponse
import com.example.sociamediaapplication.model.response.VendorProductResponse
import com.example.sociamediaapplication.model.response.VendorProductsResponse
import com.example.sociamediaapplication.model.response.WishlistResponse
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.HttpException

class MarketplaceRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.productApi

    suspend fun getVendorProducts(): VendorProductsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.getAllVendorProducts(token)
    }

    suspend fun getUserProducts(): UserProductsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.getAllUserProducts(token)
    }

    suspend fun getProductDetails(productId: Int): ProductDetailsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.getProductDetails(token, productId)
    }

    suspend fun getProductCategoryTypes(): ProductCategoriesType{
        return api.getProductCategoryTypes()
    }

    suspend fun getReviews(productId: Int): ReviewsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchReviews(token, productId)
    }

    suspend fun getCategoryProducts(categoryId: Int): UserProductsResponse {
        return api.fetchCategoryProducts(categoryId)
    }

    suspend fun reactToReview(reviewId: Int, reactionType: String): BasicResponse2{
        val token = "Bearer ${tokenManager.getToken()}"
        return api.reactToReview(token, reviewId, ReviewReactionTypeRequest(reactionType))
    }

    suspend fun reactToReviewReply(reviewId: Int, reactionType: String): BasicResponse2{
        val token = "Bearer ${tokenManager.getToken()}"
        return api.reactToReviewReply(token, reviewId, ReviewReactionTypeRequest(reactionType))
    }

    suspend fun addReview(
        productId: Int,
        rating: Int,
        comment: String
    ): AddReviewResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        val response = api.addReview(token, productId, AddReviewRequest(rating, comment))

        return response
    }

    suspend fun addReviewReply(
        reviewId: Int,
        reply: String
    ): BasicResponse2 {
        val token = "Bearer ${tokenManager.getToken()}"
        val response = api.addReviewReply(token, reviewId, ReplyReviewRequest(reply))

        return response
    }


    suspend fun addProduct(
        name: RequestBody,
        category: RequestBody,
        price: RequestBody,
        discount: RequestBody,
        specs: RequestBody,
        stock: RequestBody,
        images: List<MultipartBody.Part?>,
        description: RequestBody
    ): AddProductResponse {

        val token = "Bearer ${tokenManager.getToken()}"

        try {

            val response = api.addProduct(
                token = token,
                name = name,
                category_id = category,
                price = price,
                stock = stock,
                images = images,
                description = description,
                discount = discount,
                specs = specs
            )

            Log.d("UPLOAD_DEBUG", "SUCCESS RESPONSE = $response")
            return response

        } catch (e: HttpException) {

            val errorBody = e.response()?.errorBody()?.string()

            Log.e("UPLOAD_DEBUG", "HTTP ERROR ${e.code()}")
            Log.e("UPLOAD_DEBUG", "ERROR BODY = $errorBody")

            throw e
        }
    }

    suspend fun editProduct(
        product_id: Int,
        name: RequestBody,
        category: RequestBody,
        price: RequestBody,
        discount: RequestBody,
        stock: RequestBody,
        images: List<MultipartBody.Part?>,
        description: RequestBody,
        specs: RequestBody
    ): EditProductResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        try {

            val response = api.editProduct(
                token = token,
                name = name,
                category_id = category,
                price = price,
                stock = stock,
                images = images,
                description = description,
                discount = discount,
                specs = specs,
                id = product_id
            )

            Log.d("UPLOAD_DEBUG", "SUCCESS RESPONSE = $response")
            return response

        } catch (e: HttpException) {

            val errorBody = e.response()?.errorBody()?.string()

            Log.e("UPLOAD_DEBUG", "HTTP ERROR ${e.code()}")
            Log.e("UPLOAD_DEBUG", "ERROR BODY = $errorBody")

            throw e
        }

    }

    suspend fun addToCart(request: AddToCartRequest): BasicResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.addToCart(token, request)
    }

    suspend fun getCartProducts(): List<CartResponse> {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchCart(token)
    }

    suspend fun deleteCartProduct(productId: String): BasicResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.deleteCartProduct(token, productId)
    }

    suspend fun getWishlistProducts(): List<WishlistResponse> {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchWishlist(token)
    }

    suspend fun addToWishlist(request: AddToWishlistRequest): BasicResponse{
        val token = "Bearer ${tokenManager.getToken()}"
        return api.addToWishlist(token, request)
    }

    suspend fun removeFromWishlist(productId: Int): BasicResponse{
        val token = "Bearer ${tokenManager.getToken()}"
        return api.removeFromWishlist(token, productId.toString())
    }

    suspend fun fetchCheckoutDetails(): CheckoutDetailsResponse{
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchCheckoutDetails(token)
    }

    suspend fun deleteProdcut(productId: Int): BasicResponse2 {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.deleteProduct(token, productId)
    }
}