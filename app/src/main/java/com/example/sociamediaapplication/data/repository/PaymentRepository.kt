package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.CreateAddressRequest
import com.example.sociamediaapplication.model.request.CreateOrderRequest
import com.example.sociamediaapplication.model.request.GenerateOrderRequest
import com.example.sociamediaapplication.model.request.OrderItem
import com.example.sociamediaapplication.model.request.VerifyPaymentRequest
import com.example.sociamediaapplication.model.response.AddressResponse
import com.example.sociamediaapplication.model.response.CreateOrderResponse
import com.example.sociamediaapplication.model.response.GenerateOrderResponse

class PaymentRepository(
    private val tokenManager: TokenManager
) {

    val api = RetrofitClient.paymentApi

    suspend fun createOrder(
//        items: List<OrderItem>
        orderId: Int
    ): CreateOrderResponse{

        val token = "Bearer ${tokenManager.getToken()}"

        return api.createOrder(
            token = token,
            CreateOrderRequest(orderId)
        )
    }


    suspend fun verifyPayment(req: VerifyPaymentRequest){
        val token = "Bearer ${tokenManager.getToken()}"

        api.verifyPayment(token,req)
    }


    suspend fun createAddress(request: CreateAddressRequest){
        val token = "Bearer ${tokenManager.getToken()}"

        api.createAddress(token, request)
    }

    suspend fun fetchAddresses(): AddressResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.fetchAddresses(token)
    }

    suspend fun generateOrderId(address_id: Int): GenerateOrderResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.generateOrderId(token, GenerateOrderRequest(address_id))
    }
}