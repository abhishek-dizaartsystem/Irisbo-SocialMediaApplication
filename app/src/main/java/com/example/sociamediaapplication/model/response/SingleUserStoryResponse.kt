package com.example.sociamediaapplication.model.response

data class SingleUserStoryResponse(
    val success: Boolean,
    val data: StoryData2
)

data class StoryData2(
    val user_id: Int,
    val stories: List<SingleUserStory>
)

data class SingleUserStory(
    val id: Int,
    val user_id: Int,
    val name: String,
    val profile_image: String,
    val media_url: String,
    val media_type: String,
    val caption: String?,
    val created_at: String,
    val expires_at: String,
    val viewed: Int,
    val views_count: Int
)

