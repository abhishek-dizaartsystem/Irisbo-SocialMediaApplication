package com.example.sociamediaapplication.model

data class FeedPost(
    val id: String,
    val userName: String,
    val caption: String,
    val isFollowing: Boolean,
    val isLiked: Boolean,
    val likes: Int
)
