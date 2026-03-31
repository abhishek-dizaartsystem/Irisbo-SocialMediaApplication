package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.CreateOrderRequest
import com.example.sociamediaapplication.model.request.VerifyPaymentRequest
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.CreateOrderResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PaymentApi {

    @POST("api/marketplace/payment/create-order")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body request: CreateOrderRequest
    ): CreateOrderResponse

    @POST("api/marketplace/payment/verify-payment")
    suspend fun verifyPayment(
        @Body request: VerifyPaymentRequest
    ): BasicResponse
}