package com.example.sociamediaapplication.model.response

data class TopVideosResponse(
    val success: Boolean,
    val data: List<TopVideo>
)

data class TopVideo(
    val video_id: Int,
    val title: String,
    val thumbnail_url: String,
    val duration_seconds: Int,

    val views_count: Int,
    val likes_count: Int,
    val comments_count: Int,
    val shares_count: Int,

    val total_watch_time_seconds: String,
    val avg_watch_time_seconds: String,

    val completion_rate: String,
    val performance_score: String
)