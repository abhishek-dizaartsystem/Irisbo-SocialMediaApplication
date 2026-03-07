package com.example.sociamediaapplication.model.response

data class GroupPostsResponse(
    val success: Boolean,
    val total_pages: Int,
    val total_posts: Int,


    val posts: List<GroupPostReponse>
)

data class GroupPostReponse(
    val id: Int,
    val user_id: Int,
    val caption: String,
    val mediaType: String,
    val media: String,
    val likes_count: Int,
    val shares_count: Int,
    val tags: List<Any>,
    val created_at: String,
    val username: String,
    val profile_img: String,
    val is_liked: Boolean,
    val is_saved: Boolean,
    val media_files: List<String>,
)
