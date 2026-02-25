package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.RazorpayOrder

data class CreateOrderResponse(
    val success: Boolean,
    val order: RazorpayOrder
)
