package com.example.sociamediaapplication.model.response

data class ConversationsResponse(
    val success: Boolean,
    val message: String,
    val conversations: List<Conversation>,
    val pagination: PaginationResponse
)

data class Conversation(
    val conversation_id: Int,
    val type: String,
    val last_message_id: Int?,
    val last_sender_id: Int?,
    val last_message_at: String?,
    val is_active: Int,
    val conversation_created_at: String,
    val conversation_updated_at: String,
    val last_read_message_id: Int?,
    val last_read_at: String?,
    val is_muted: Int,
    val is_archived: Int,
    val other_user_id: Int,
    val other_user_name: String,
    val other_user_username: String,
    val other_user_profile_image: String,
    val message_id: Int?,
    val message_sender_id: Int?,
    val message_type: String?,
    val content: String?,
    val is_edited: Int?,
    val edited_at: String?,
    val is_deleted: Int?,
    val deleted_at: String?,
    val message_created_at: String?,
    val unread_count: Int // is_seen: Int       Missing
)