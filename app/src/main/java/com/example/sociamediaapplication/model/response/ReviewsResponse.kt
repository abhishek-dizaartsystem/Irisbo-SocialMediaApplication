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
    val vendor_reply: String?,
    val vendor_reply_at: String?,
    val review_likes: Int,
    val review_dislikes: Int,
    val user_review_reaction: String,
    val reply_likes: Int,
    val reply_dislikes: Int,
    val user_reply_reaction: String
)