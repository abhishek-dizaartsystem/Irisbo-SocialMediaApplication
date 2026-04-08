package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.RazorpayOrder

data class CreateOrderResponse(
    val success: Boolean,
    val data: RazorPayResponse
)

data class RazorPayResponse(
    val razorpay_order_id: String,
    val amount: Int,
    val currency: String
)
