package com.example.sociamediaapplication.model.response

data class FriendStoriesResponse(
    val success: Boolean,
    val data: List<StoryData>
)

data class StoryData(
    val user: DataUser,
    val has_unseen: Boolean,
    val stories: List<DataStory>
)

data class DataUser(
    val id: Int,
    val name: String
)

data class DataStory(
    val id: Int,
    val mediaUrl: String,
    val media_type: String,
    val caption: String?,
    val created_at: String,
    val expires_at: String,
    val viewed: Boolean,
    val views_count: Int
)