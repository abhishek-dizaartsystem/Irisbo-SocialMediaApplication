package com.example.sociamediaapplication.model.response

data class ReelListResponse(
    val success: Boolean,
    val total_reel: Int,
    val reels: List<Reel>
)
