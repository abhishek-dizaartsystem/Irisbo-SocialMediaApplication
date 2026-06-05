package com.example.sociamediaapplication.model.response

data class AnalyticsDashboardResponse(
    val success: Boolean,
    val data: AnalyticsDashboardData
)

data class AnalyticsDashboardData(
    val summary: ChannelAnalyticsSummary,
    val videos: AnalyticsVideos,
    val topVideos: List<TopVideoAnalytics>
)

data class ChannelAnalyticsSummary(
    val user_id: Int,
    val name: String,
    val username: String,
    val total_videos_count: Int,
    val published_videos_count: Int,
    val total_views_count: Int,
    val total_unique_viewers_count: Int,
    val total_watch_time_seconds: Int,
    val avg_watch_time_seconds: String,
    val total_completed_views_count: Int,
    val avg_completion_rate: String,
    val total_likes_count: Int,
    val total_dislikes_count: Int,
    val total_comments_count: Int,
    val total_shares_count: Int,
    val subscriber_count: Int,
    val saved_videos_count: Int,
    val overall_engagement_rate: String,
    val last_video_published_at: String?,
    val created_at: String,
    val updated_at: String
)

data class AnalyticsVideos(
    val pagination: PaginationResponse,
    val data: List<VideoAnalytics2>
)

data class VideoAnalytics2(
    val video_id: Int,
    val user_id: Int,
    val title: String,
    val thumbnail_url: String,
    val duration_seconds: Int,
    val visibility: String,
    val status: String,
    val published_at: String?,
    val created_at: String,
    val views_count: Int,
    val likes_count: Int,
    val dislikes_count: Int,
    val comments_count: Int,
    val shares_count: Int,
    val unique_viewers_count: Int,
    val total_watch_time_seconds: String,
    val avg_watch_time_seconds: String,
    val completed_views_count: Int,
    val completion_rate: String,
    val avg_progress_percent: String,
    val last_viewed_at: String?,
    val engagement_rate: String
)

data class TopVideoAnalytics(
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