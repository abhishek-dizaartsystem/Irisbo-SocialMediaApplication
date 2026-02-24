package com.example.sociamediaapplication.data.repository

import android.util.Log
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.AddToCartRequest
import com.example.sociamediaapplication.model.response.AddProductResponse
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.CartResponse
import com.example.sociamediaapplication.model.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody

class MarketplaceRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.productApi

    suspend fun getProducts(): List<ProductResponse> {
        return api.getAllProducts()
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


    suspend fun addProduct(
        name: RequestBody,
        category: RequestBody,
        price: RequestBody,
        stock: RequestBody,
        image: MultipartBody.Part?,
        description: RequestBody
    ): AddProductResponse {

        val token = "Bearer ${tokenManager.getToken()}"

        try {

            val response = api.addProduct(
                token = token,
                name = name,
                category = category,
                price = price,
                stock = stock,
                image = image,
                description = description
            )

            Log.d("UPLOAD_DEBUG", "SUCCESS RESPONSE = $response")
            return response

        } catch (e: retrofit2.HttpException) {

            val errorBody = e.response()?.errorBody()?.string()

            Log.e("UPLOAD_DEBUG", "HTTP ERROR ${e.code()}")
            Log.e("UPLOAD_DEBUG", "ERROR BODY = $errorBody")

            throw e
        }
    }
}