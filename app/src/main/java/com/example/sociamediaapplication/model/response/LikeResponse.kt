package com.example.sociamediaapplication.model.response

data class LikeResponse(
    val success: Boolean,
    val liked: Boolean,
    val likes_count: Int
)
