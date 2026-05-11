package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.viewmodel.AuthUiState

data class VideoCategoryResponse(
    val success: Boolean,
    val data: List<VideoCategory>
)

data class VideoCategory(
    val id: Int,
    val name: String,
    val created_at: String
)
