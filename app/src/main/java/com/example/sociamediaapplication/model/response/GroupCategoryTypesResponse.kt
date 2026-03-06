package com.example.sociamediaapplication.model.response

data class GroupCategoryTypesResponse(
    val success: Boolean,
    val total: Int,
    val categories: List<GroupCategoryType>
)

data class GroupCategoryType(
    val id: Int,
    val name: String,
)
