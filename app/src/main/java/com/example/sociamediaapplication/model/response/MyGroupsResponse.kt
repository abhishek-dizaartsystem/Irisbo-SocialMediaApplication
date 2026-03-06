package com.example.sociamediaapplication.model.response

data class MyGroupsResponse(
    val success: Boolean,
    val page: Int,
    val total_pages: Int,
    val total_groups: Int,
    val groups: List<MyGroupResponse>
)

data class MyGroupResponse(
    val id: Int,
    val name: String,
    val cover_image: String,
    val privacy: String,
    val created_at: String,
    val role: String,
    val member_count: Int
)