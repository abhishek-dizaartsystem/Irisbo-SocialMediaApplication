package com.example.sociamediaapplication.model.response

data class EventDetailsResponse(
    val success: Boolean,
    val event: EventDetails
)

data class EventDetails(
    val id: Int,
    val title: String,
    val description: String,
    val cover_image: String?,
    val start_time: String,
    val end_time: String,
    val location_name: String?,
    val created_by: Int,
    val group_id: Int? = null,
    val page_id: Int? = null,
    val status: String,
    val created_at: String,
    val updated_at: String
)