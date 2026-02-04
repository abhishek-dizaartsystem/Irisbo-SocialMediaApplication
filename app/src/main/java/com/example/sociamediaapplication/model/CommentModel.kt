package com.example.sociamediaapplication.model

data class CommentModel(
    val id: String,
    val likes: Int,
    val dislikes: Int,
    val replies: Int,
    val isLiked: Boolean,
    val isDisliked: Boolean,

)