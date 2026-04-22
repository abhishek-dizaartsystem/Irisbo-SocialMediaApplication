package com.example.sociamediaapplication.model

data class StoryUi(
    val id: Int,
    val mediaUrl: String,
    val mediaType: String,
    val profileImage: String? = null,
    val userName: String
)
