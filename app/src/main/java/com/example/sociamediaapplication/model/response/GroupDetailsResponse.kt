package com.example.sociamediaapplication.model.response

data class GroupDetailsResponse(
    val success: Boolean,
    val group: GroupDetails
)

data class GroupDetails(
    val id: Int,
    val name: String,
    val slug: String,
    val description: String,
    val cover_image: String,
    val privacy: String,
    val approval_required: Boolean,
    val only_admin_post: Boolean,
    val created_at: String,
    val creatorId: Int,
    val creator_name: String,
    val category_id: Int,
    val category_name: String,
    val member_count: Int,
    val membership_status: String,
    val isMember: Boolean
)