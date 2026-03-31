package com.example.sociamediaapplication.model.response

data class SummaryResponse(
    val total_reviews: Int,
    val average_rating: Float,
    val rating_5: Int,
    val rating_4: Int,
    val rating_3: Int,
    val rating_2: Int,
    val rating_1: Int
)
