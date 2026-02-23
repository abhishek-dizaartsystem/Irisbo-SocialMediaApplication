package com.example.sociamediaapplication.model.response

data class ProfileResponse(
    val name: String,
    val username: String,
    val bio: String,
    val email: String,
    val education: String,
    val work: String,
    val phone: String? = null,
    val profile_img: String?,
    val cover_img: String?
)