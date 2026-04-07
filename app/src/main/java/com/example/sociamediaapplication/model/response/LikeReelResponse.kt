package com.example.sociamediaapplication.model.response

data class LikeReelResponse(
    val success: Boolean,
    val counts: Count,
    val user_reaction: Reaction
)
