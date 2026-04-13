package com.example.sociamediaapplication.model.response

data class FriendStatusResponse(
    val success: Boolean,
    val data: Status
)

data class Status(
    val status: String,
    val friendship: Friendship
)

data class Friendship(
    val id: Int,
    val user_one_id: Int,
    val user_two_id: Int,
    val requester_id: Int,
    val status: String,
    val action_user_id: Int,
    val created_at: String,
    val updated_at: String
)