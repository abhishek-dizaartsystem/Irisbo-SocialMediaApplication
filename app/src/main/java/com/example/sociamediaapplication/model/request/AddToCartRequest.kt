package com.example.sociamediaapplication.model.request

data class AddToCartRequest(
    val product_id: String,
    val quantity: Int
)
