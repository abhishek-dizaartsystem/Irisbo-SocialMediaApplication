package com.example.sociamediaapplication.model.response

data class EventsResponse(
    val success: Boolean,
    val events: List<EventResponse>
)

data class EventResponse(
    val id: Int,
    val title: String,
    val cover_image: String?,
    val start_time: String,
    val location_name: String?,
    val status: String,
    val creator: String
)