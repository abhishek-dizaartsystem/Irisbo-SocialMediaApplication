package com.example.sociamediaapplication.model.response

data class ReviewsResponse(
    val success: Boolean,
    val reviews: List<Review>,
    val pagination: PaginationResponse,
    val summary: SummaryResponse
)

data class Review(
    val id: Int,
    val rating: Int,
    val review: String,
    val created_at: String,
    val reviewer_username: String,
    val reply: String?,
    val reply_created_at: String?,
    val review_likes: Int,
    val review_dislikes: Int,
    val user_review_reaction: String,
    val reply_likes: Int,
    val reply_dislikes: Int,
    val user_reply_reaction: String
)