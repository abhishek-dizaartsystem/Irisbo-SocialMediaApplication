package com.example.sociamediaapplication.model.response

data class CartResponse(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val product_image: String?,
    val username: String? = null
)
