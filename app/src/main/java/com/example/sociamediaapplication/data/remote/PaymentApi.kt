package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.CreateAddressRequest
import com.example.sociamediaapplication.model.request.CreateOrderRequest
import com.example.sociamediaapplication.model.request.GenerateOrderRequest
import com.example.sociamediaapplication.model.request.VerifyPaymentRequest
import com.example.sociamediaapplication.model.response.AddressResponse
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.CreateOrderResponse
import com.example.sociamediaapplication.model.response.GenerateOrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PaymentApi {

    @POST("api/marketplace/payments/create-order")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body request: CreateOrderRequest//order_id
    ): CreateOrderResponse

    @POST("api/marketplace/payments/verify")
    suspend fun verifyPayment(
        @Header("Authorization") token: String,
        @Body request: VerifyPaymentRequest
    ): BasicResponse

    @POST("api/marketplace/addresses")
    suspend fun createAddress(
        @Header("Authorization")token: String,
        @Body request: CreateAddressRequest
    )

    @GET("api/marketplace/addresses")
    suspend fun fetchAddresses(
        @Header("Authorization") token: String,
    ): AddressResponse

    @POST("api/marketplace/orders/create")
    suspend fun generateOrderId(
    @Header("Authorization") token: String,
    @Body request: GenerateOrderRequest
    ): GenerateOrderResponse


}