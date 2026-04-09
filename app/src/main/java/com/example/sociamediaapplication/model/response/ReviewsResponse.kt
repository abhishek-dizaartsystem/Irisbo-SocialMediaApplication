package com.example.sociamediaapplication.model.response

data class ReviewsResponse(
    val success: Boolean,
    val data: ReviewData
)

data class ReviewData(
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
    val reply_id: Int,
    val reply_created_at: String?,
    val review_like_count: Int,
    val review_dislike_count: Int,
    val user_review_reaction: String,
    val review_reply_like_count: Int,
    val review_reply_dislike_count: Int,
    val user_review_reply_reaction: String
)