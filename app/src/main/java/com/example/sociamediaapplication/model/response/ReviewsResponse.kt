package com.example.sociamediaapplication.model.response

data class ReviewsResponse(
    val success: Boolean,
    val total_reviews: Int,
    val reviews: List<Review>
)

data class Review(
    val id: Int,
    val rating: Int,
    val comment: String,
    val created_at: String,
    val username: String,
)