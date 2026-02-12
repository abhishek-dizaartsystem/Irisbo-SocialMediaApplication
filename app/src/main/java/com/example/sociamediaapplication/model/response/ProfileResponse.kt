package com.example.sociamediaapplication.model.response

data class ProfileResponse(
    val userName: String,
    val name: String,
    val bio: String,
    val profileImageUrl: String?,
    val coverImageUrl: String?
)