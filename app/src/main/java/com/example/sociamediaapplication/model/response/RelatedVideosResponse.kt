package com.example.sociamediaapplication.model.response

data class RelatedVideosResponse(
    val success: Boolean,
    val data: List<RelatedVideo>
)

data class RelatedVideo(
    val id: Int,
    val user_id: Int,
    val title: String,
    val thumbnail_url: String,
    val duration_seconds: Int,
    val views_count: Int,
    val likes_count: Int,
    val comments_count: Int,
    val created_at: String,
    val creator_name: String,
    val creator_username: String
)