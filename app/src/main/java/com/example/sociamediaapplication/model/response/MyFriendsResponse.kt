package com.example.sociamediaapplication.model.response

data class MyFriendsResponse(
    val success: Boolean,
    val data: MyFriends
)

data class MyFriends(
    val friendship_id: Int,
    val id: Int,
    val name: String,
    val username: String,
    val profile_image: String,
    val friends_since: String
)
