package com.example.sociamediaapplication.model.response

data class PagesResponse(
    val success: Boolean,
    val data: List<PageResponse>
)

data class PageResponse(
    val  id: Int,
    val name: String,
    val slug: String,
    val bio: String,
    val profile_image: String?,
    val cover_image: String?,
    val website: String,
    val phone: String,
    val email: String,
    val address: String,
    val category_id: Int,
    val created_at: String,
    val category_name: String,
    val followers_count: Int,
    val is_following: Int,
    val user_role: String?
)