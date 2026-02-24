package com.example.sociamediaapplication.model.response

data class WishlistResponse(
    val id: Int,
    val name: String,
    val product_image: String?,
    val price: String,
    val username: String
)