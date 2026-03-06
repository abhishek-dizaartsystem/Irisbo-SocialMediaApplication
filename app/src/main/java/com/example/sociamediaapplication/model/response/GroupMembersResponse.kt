package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Group

data class GroupMembersResponse(
    val success: Boolean,
    val group: Group,
    val total_members: Int,
    val members: List<Member>
)

data class Member(
    val user_id: Int,
    val name: String,
    val username: String,
    val profile_image: String,
    val role: String,
    val status: String,
    val joined_at: String
)

