package com.example.sociamediaapplication.model.response

data class GroupPostDetailsResponse(
    val success: Boolean,
    val post: GroupPostDetails
)

data class GroupPostDetails(
    val id: Int,
    val user_id: Int,
    val group_id: Int,
    val caption: String,
    val media: String,
    val mediaType: String,
    val likes_count: Int,
    val shares_count: Int,
    val tags: List<Any>,
    val created_at: String,
    val post_type: String,
    val privacy: String,
    val username: String,
    val profile_img: String,
    val is_liked: Boolean,
    val is_saved: Boolean,
    val media_urls: List<String>,
    val profile_image_url: String
)
