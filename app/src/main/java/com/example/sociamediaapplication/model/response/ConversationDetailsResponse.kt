package com.example.sociamediaapplication.model.response

data class ConversationDetailsResponse(
    val success: Boolean,
    val message: String,
    val data: ConversationDetail
)

data class ConversationDetail(
    val conversation_id: Int,
    val type: String,
    val last_message_id: Int,
    val last_sender_id: Int,
    val last_message_at: String,
    val is_active: Int,
    val created_at: String,
    val updated_at: String,
    val current_user_id: Int,
    val last_read_message_id: Int,
    val last_read_at: String,
    val is_muted: Int,
    val is_archived: Int,
    val other_user_id: Int,
    val other_user_name: String,
    val other_user_username: String,
    val other_user_email: String,
    val other_user_profile_image: String
)
