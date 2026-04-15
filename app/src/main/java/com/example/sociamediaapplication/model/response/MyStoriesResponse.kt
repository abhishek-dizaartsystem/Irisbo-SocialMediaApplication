package com.example.sociamediaapplication.model.response

data class MyStoriesResponse(
    val success: Boolean,
    val data: List<MyStory>
)

data class MyStory(
    val id: Int,
    val mediaUrl: String,
    val media_type: String,
    val caption: String?,
    val created_at: String,
    val expires_at: String,
    val views_count: Int
)
