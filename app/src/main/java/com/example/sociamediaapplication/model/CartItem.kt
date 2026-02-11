package com.example.sociamediaapplication.model

data class CartItem(
    val productId: String,
    val painter: Int,
    val price: String,
    val productName: String,
    val productCount: Int
)