package com.example.sociamediaapplication.model.response

data class PostsListResponse(
    val success: Boolean,
    val total_posts: Int,
    val posts: List<PostResponse>
)

