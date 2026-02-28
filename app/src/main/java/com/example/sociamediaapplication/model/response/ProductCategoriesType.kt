package com.example.sociamediaapplication.model.response

data class ProductCategoriesType(
    val success: Boolean,
    val categories: List<CategoriesType>
)

data class CategoriesType(
    val id: Int,
    val name: String,
    val image: String
)
