package com.example.sociamediaapplication.model.response

data class ReceivedRequestResponse(
    val success: Boolean,
    val data: List<ReceivedRequest>
)

data class ReceivedRequest(
    val friendship_id: Int,
    val created_at: String,
    val updated_at: String,
    val id: Int,
    val name: String,
    val username: String,
    val profile_image: String,
    val mutual_friends_count: Int
)