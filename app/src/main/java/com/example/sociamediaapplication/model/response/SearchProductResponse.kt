package com.example.sociamediaapplication.model.response

data class SearchProductResponse(
    val success: Boolean,
    val page: String,
    val total_products: Int,
    val products: List<UserProductResponse>
)
