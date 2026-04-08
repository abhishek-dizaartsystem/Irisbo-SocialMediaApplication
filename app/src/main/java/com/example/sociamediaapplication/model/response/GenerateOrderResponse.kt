package com.example.sociamediaapplication.model.response

data class GenerateOrderResponse(
    val success: Boolean,
    val message: String,
    val data: OrderResp
)

data class OrderResp(
    val id: Int
)
