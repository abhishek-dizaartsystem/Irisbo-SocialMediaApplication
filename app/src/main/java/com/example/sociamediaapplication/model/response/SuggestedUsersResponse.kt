package com.example.sociamediaapplication.model.response

data class SuggestedUsersResponse(
    val success: Boolean,
    val data: List<SuggestedUser>
)

data class SuggestedUser(
    val id: Int,
    val name: String,
    val username: String,
    val profile_image: String,
    val mutual_friends_count: Int
)
