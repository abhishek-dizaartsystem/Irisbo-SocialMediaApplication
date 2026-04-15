package com.example.sociamediaapplication.model.response

data class ProfileResponse(
    val success: Boolean,
    val data: ProfileModel
)

data class ProfileModel(
    val id: Int,
    val name: String,
    val username: String,
    val email: String?,
    val bio: String?,
    val education: String?,
    val work: String?,
    val phone: String? = null,
    val profile_image: String?,
    val cover_img: String?,
    val create_at: String
)