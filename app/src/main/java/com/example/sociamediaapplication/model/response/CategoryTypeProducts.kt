package com.example.sociamediaapplication.model.response

data class CategoryProductsResponse(
    val success: Boolean,
    val count: Int,
    val products: List<UserProductsResponse>
)