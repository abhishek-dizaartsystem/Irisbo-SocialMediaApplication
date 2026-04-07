package com.example.sociamediaapplication.model.response

data class AddGroupResponse(
    val success: Boolean,
    val message: String,
    val group: AddGroup
)

data class AddGroup(
    val id: Int,
    val name: String,
    val description: String,
    val cover_image: String,
    val privacy: String,
    val approval_required: Int,
    val only_admin_post: Int,
    val created_by: String,
    val created_at: String,
    val updated_at: String,
    val category_id: Int?,
    val cover_image_url: String,
    val is_deleted: Int,
)