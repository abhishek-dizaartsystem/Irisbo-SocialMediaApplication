package com.example.sociamediaapplication.model.response

data class CategoryProducts(
    val success: Boolean,
    val data: List<UserProductResponse>
)
