package com.example.sociamediaapplication.model.response

data class ChannelAnalyticsResponse(
    val success: Boolean,
    val data: AnalyticsResponse
)

data class AnalyticsResponse(
    val user_id: Int,
    val name: String,
    val username: String,
    val total_videos_count: Int,
    val published_videos_count: Int,
    val total_views_count: Int,
    val total_unique_viewers_count: Int,
    val total_watch_time_seconds: Int,
    val avg_watch_time_seconds: Float,
    val total_completed_views_count: Int,
    val avg_completion_rate: Float,
    val total_likes_count: Int,
    val total_dislikes_count: Int,
    val total_comments_count: Int,
    val total_shares_count: Int,
    val subscriber_count: Int,
    val saved_videos_count: Int,
    val overall_engagement_rate: Float,
    val last_video_published_at: String?,
    val created_at: String,
    val updated_at: String
)