package com.example.sociamediaapplication.model.response

data class ProductResponse(
    val id: Int,
    val name: String,
    val category: String?,
    val product_image: String?,
    val price: String,
    val stock: Int,
    val created_at: String,
    val username: String
)
