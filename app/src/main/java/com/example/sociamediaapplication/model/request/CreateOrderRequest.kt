package com.example.sociamediaapplication.model.request

data class CreateOrderRequest(
    val order_id: Int
)

data class OrderItem(
    val product_id: Int,
    val quantity: Int
)