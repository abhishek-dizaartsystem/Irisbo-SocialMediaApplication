package com.example.sociamediaapplication.model.response

data class GetMyVideosResponse(
    val success: Boolean,
    val videos: List<MyVideoResponse>,
    val pagination: PaginationResponse
)

data class MyVideoResponse(
    val id: Int,
    val user_id: Int,
    val title: String,
    val description: String,
    val video_url: String,
    val thumbnail_url: String,
    val duration_seconds: Int,
    val visibility: String,
    val status: String,
    val category_id: Int,
    val views_count: Int,
    val likes_count: Int,
    val dislikes_count: Int,
    val comments_count: Int,
    val shares_count: Int,
    val tags: List<String>,
    val is_comments_enabled: Int,
    val published_at: String?,
    val created_at: String,
    val updated_at: String,
    val category_name: String
)