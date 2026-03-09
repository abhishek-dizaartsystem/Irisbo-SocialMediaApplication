package com.example.sociamediaapplication.model.response

data class PagePostsResponse(
    val success: Boolean,
    val data: List<PagePostData>
)

data class PagePostData(
    val id: Int,
    val caption: String,
    val media: String?,
    val media_type: String?,
    val created_at: String,
    val user_id: Int,
    val username: String,
    val profile_image: String
)
