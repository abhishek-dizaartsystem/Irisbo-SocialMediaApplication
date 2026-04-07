package com.example.sociamediaapplication.model.response

data class Reel(
    val id: Int,
    val user_id: Int,
    val video_url: String,
    val caption: String?,
    val likes_count: Int,
    val user_name: String?,
    val user_profile_image: String?,
    val is_liked: Int,
    val is_saved: Int
)