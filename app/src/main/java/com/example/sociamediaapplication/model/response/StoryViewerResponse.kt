package com.example.sociamediaapplication.model.response

data class StoryViewerResponse(
    val success: Boolean,
    val data: List<StoryViewer>
)

data class StoryViewer(
    val viewer_id: Int,
    val name: String,
    val profile_image: String,
    val viewed_at: String
)