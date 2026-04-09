package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.viewmodel.AuthUiState

data class GroupsResponse(
    val success: Boolean,
    val page: Int,
    val total_pages: Int,
    val total_groups: Int,
    val groups: List<GroupResponse>
)

data class GroupResponse(
    val id: Int,
    val name: String,
    val slug: String,
    val cover_image: String?,
    val privacy: String,
    val created_at: String,
    val member_count: Int
)
