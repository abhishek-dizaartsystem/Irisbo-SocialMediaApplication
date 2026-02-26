package com.example.sociamediaapplication.model.response

data class UserProductsResponse(
    val success: Boolean,
    val total_products: Int,
    val products: List<ProductResponse>
)
