package com.example.sociamediaapplication.model.response

data class MessagesResponse(
    val success: Boolean,
    val message: String,
    val conversationId: Int,
    val messages: List<MessageResponse>,
    val pagination: PaginationResponse
)

data class MessageResponse(
    val id: Int,
    val conversation_id: Int,
    val sender_id: Int,
    val message_type: String,
    val content: String,
    val reply_to_message_id: String?,
    val client_temp_id: String?,
    val is_edited: Int,
    val edited_at: String?,
    val is_deleted: Int,
    val deleted_at: String?,
    val created_at: String,
    val updated_at: String,
    val sender_name: String,
    val sender_username: String,
    val sender_profile_image: String,
    val reply_message_id: Int?,
    val reply_message_content: String?,
    val reply_message_type: String?,
    val reply_message_sender_id: Int?,
    val attachments: List<String>        // Not confirmed
)