package com.example.sociamediaapplication.model.response

data class PostResponse(
    val id: Int,
    val user_id: Int,
    val caption: String?,
    val media: List<String>?,
    val media_type: String?,
    val likes_count: Int,
    val shares_count: Int?,
    val tags: String?,
    val created_at: String,
    val username: String,
    val profile_image: String?,
    val is_liked: Int,
    val is_saved: Int,

)
