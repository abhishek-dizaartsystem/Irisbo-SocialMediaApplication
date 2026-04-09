package com.example.sociamediaapplication.model.response

data class GroupPostsResponse(
    val success: Boolean,
    val posts: List<GroupPostResponse>
)

data class GroupPostResponse(
    val id: Int,
    val user_id: Int,
    val caption: String,
    val mediaType: String,
    val media: List<String>,
    val likes: Int,
    val shares_count: Int,
    val tags: String?,
    val created_at: String,
    val username: String,
    val profile_image: String?,
    val is_liked: Boolean,
    val is_saved: Boolean,
    val user_reaction: String,
    val media_files: List<String>,
)
