package com.example.sociamediaapplication.model.request

data class AddCommentRequest(
    val content: String,
    val parent_id: Int? = null
)