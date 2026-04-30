package com.example.sociamediaapplication.model

data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val msgTime: String = "12:00 PM"
)