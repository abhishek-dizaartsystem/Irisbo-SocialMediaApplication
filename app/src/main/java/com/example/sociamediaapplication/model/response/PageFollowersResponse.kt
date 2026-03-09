package com.example.sociamediaapplication.model.response

data class PageFollowersResponse(
    val success: Boolean,
    val data: List<PageFollower>,
    val pagination: PageFollowersPagination
)

data class PageFollower(
    val id: Int,
    val username: String,
    val profile_image: String
)

data class PageFollowersPagination(
    val total: Int,
    val page: Int,
    val limit: Int
)
