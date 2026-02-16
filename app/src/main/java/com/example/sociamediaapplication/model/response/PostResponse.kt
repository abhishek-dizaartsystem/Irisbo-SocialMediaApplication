package com.example.sociamediaapplication.model.response

data class PostResponse(
    val id: Int,
    val user_id: Int,
    val caption: String?,
    val media: String?,
    val media_type: String?,
    val likes_count: Int?,
    val shares_count: Int?,
    val tags: String?,
    val created_at: String,
    val username: String,
    val profile_image: String?,
    val media_urls: List<String>,
    val profile_image_url: String?
)
