package com.example.sociamediaapplication.model.response

data class LikeResponse(
    val success: Boolean,
    val liked: Boolean,
    val likes_count: Int
)

data class LikePostResponse(
    val success: Boolean,
    val data: LikeData
)

data class LikeData(
    val counts: Count,
    val user_reaction: Reaction
)

data class Count(
    val likes: Int,
    val dislikes: Int
)

data class Reaction(
    val reaction: String
)