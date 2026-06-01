package com.example.sociamediaapplication.model.response

data class VideoCommentsResponse(
    val success: Boolean,
    val total_comments: Int,
    val comments: List<VideoComment>
)

data class VideoComment(
    val id: Int,
    val parent_id: Int?,
    val entity_type: String,
    val entity_id: Int,
    val user_id: Int,
    val content: String,
    val created_at: String,
    val updated_at: String?,
    val is_deleted: Int,
    val deleted_at: String?,
    val name: String,
    val username: String,
    val profile_image: String,
    val likes: Int,
    val dislikes: Int,
    val user_reaction: String?,
    val replies: List<String>
)