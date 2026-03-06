package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.CreateOrderRequest
import com.example.sociamediaapplication.model.request.OrderItem
import com.example.sociamediaapplication.model.request.VerifyPaymentRequest
import com.example.sociamediaapplication.model.response.CreateOrderResponse

class PaymentRepository(
    private val tokenManager: TokenManager
) {

    val api = RetrofitClient.paymentApi

    suspend fun createOrder(
        items: List<OrderItem>
    ): CreateOrderResponse{

        val token = "Bearer ${tokenManager.getToken()}"

        return api.createOrder(
            token = token,
            CreateOrderRequest(items)
        )
    }


    suspend fun verifyPayment(req: VerifyPaymentRequest) =
        api.verifyPayment(req)
}