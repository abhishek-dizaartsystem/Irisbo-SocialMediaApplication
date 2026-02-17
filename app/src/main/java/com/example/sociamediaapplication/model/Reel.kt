package com.example.sociamediaapplication.model

//data class Reel(
//    val videoUrl: String,
//    val isLiked: Boolean = false,
//    val likeCount: Int = 0,
//    val isSaved: Boolean = false
//)

data class Reel(
    val id: Int,
    val user_id: Int,
    val video_url: String,
    val caption: String?,
    val likes_count: Int,
    val username: String,
    val profile_image_url: String?,
    val is_liked: Boolean
)

