package com.example.sociamediaapplication.model.response

data class PostResponse(
    val id: Int,
    val user_id: Int,
    val caption: String?,
    var media: List<String>?,
    val media_type: String?,
    val likes_count: Int,
    val tags: String?,
    val created_at: String,
    val username: String,
    val profile_image: String?,
    val user_reaction: String,
    val is_saved: Boolean,
)


data class GlobalPostsResponse(
    val success: Boolean,
    val total_posts: Int,
    val posts: List<PostResponse>
)