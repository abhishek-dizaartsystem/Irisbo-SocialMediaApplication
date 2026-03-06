package com.example.sociamediaapplication.model.request

data class CreateOrderRequest(
    val items: List<OrderItem>
)

data class OrderItem(
    val product_id: Int,
    val quantity: Int
)