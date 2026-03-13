package com.example.sociamediaapplication.model.response

data class EventsResponse(
    val success: Boolean,
    val events: List<EventResponse>
)

data class EventResponse(
    val id: Int,
    val title: String,
    val cover_image: String?,
    val start_time: String?,
    val location_name: String?,
    val status: String,
    val category: String?,
    val creator: String,
    val likes: Int,
    val dislikes: Int,
    val participants: Int,
    val user_reaction: String?,
    val is_participating: Int
)