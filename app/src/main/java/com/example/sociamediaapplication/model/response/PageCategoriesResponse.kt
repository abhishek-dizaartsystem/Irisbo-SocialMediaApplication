package com.example.sociamediaapplication.model.response

data class PageCategoriesResponse(
    val success: Boolean,
    val categories: List<PageCategory>
)

data class PageCategory(
    val id: Int,
    val name: String
)