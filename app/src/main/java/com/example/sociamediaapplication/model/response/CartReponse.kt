package com.example.sociamediaapplication.model.response

data class CartResponse(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val discount: String,
    val discounted_price: String,
    val product_image: List<String>,
    val username: String? = null
)
