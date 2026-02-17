package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Reel

data class ReelListResponse(
    val success: Boolean,
    val total_reel: Int,
    val reels: List<Reel>
)
