package com.example.sociamediaapplication.model.response

data class MyEventsResponse(
    val success: Boolean,
    val count: Int,
    val events: List<MyEvent>
)

data class MyEvent(
    val id: Int,
    val title: String,
    val description: String,
    val cover_image: String,
    val location_name: String,
    val start_time: String,
    val end_time: String,
    val created_at: String,
    val updated_at: String
)
