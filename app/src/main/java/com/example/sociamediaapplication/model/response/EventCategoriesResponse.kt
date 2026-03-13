package com.example.sociamediaapplication.model.response

data class EventCategoriesResponse(
    val success: Boolean,
    val count: Int,
    val categories: List<PageCategory>
)
