package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.CreateOrderRequest
import com.example.sociamediaapplication.model.request.VerifyPaymentRequest

class PaymentRepository(
) {

    val api = RetrofitClient.paymentApi

    suspend fun createOrder(amount: Double) =
        api.createOrder(CreateOrderRequest(amount))

    suspend fun verifyPayment(req: VerifyPaymentRequest) =
        api.verifyPayment(req)
}