package com.example.sociamediaapplication.model.response

data class PaginationResponse(
    val page: Int,
    val limit: Int,
    val total: Int,
    val totalPages: Int
)
