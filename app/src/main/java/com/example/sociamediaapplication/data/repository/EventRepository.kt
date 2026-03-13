package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.response.EventCategoriesResponse
import com.example.sociamediaapplication.model.response.EventDetailsResponse
import com.example.sociamediaapplication.model.response.EventsResponse
import com.example.sociamediaapplication.model.response.MyEventsResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

class EventRepository(
    private val tokenManager: TokenManager
) {
    private val api = RetrofitClient.eventApi

    suspend fun fetchEvents(): EventsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.fetchEvents(token)
    }

    suspend fun fetchMyEvents(): MyEventsResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        return api.fetchMyEvents(token)
    }

    suspend fun createEvent(
        context: Context,
        title: RequestBody,
        location_name: RequestBody,
        description: RequestBody,
        start_time: RequestBody,
        end_time: RequestBody,
        cover_image_uri: Uri,
        category_id: RequestBody
    ){
        val token = "Bearer ${tokenManager.getToken()}"

        val file = uriToFile(cover_image_uri, context)

        val mime = context.contentResolver.getType(cover_image_uri) ?: "image/jpeg"

        val requestFile = file.asRequestBody(
            mime.toMediaTypeOrNull()
        )

        val imagePart = MultipartBody.Part.createFormData(
            "cover_image",
            file.name,
            requestFile
        )

        val titleBody = title

        return api.createEvent(token, title, location_name, description, start_time, end_time, category_id, imagePart)
    }

    suspend fun updateEvent(
        context: Context,
        eventId: Int,
        category_id: RequestBody,
        title: RequestBody,
        location_name: RequestBody,
        description: RequestBody,
        start_time: RequestBody,
        end_time: RequestBody,
        cover_image_uri: Uri
    ){
        val token = "Bearer ${tokenManager.getToken()}"

        val file = uriToFile(cover_image_uri, context)

        val mime = context.contentResolver.getType(cover_image_uri) ?: "image/jpeg"

        val requestFile = file.asRequestBody(
            mime.toMediaTypeOrNull()
        )

        val imagePart = MultipartBody.Part.createFormData(
            "cover_image",
            file.name,
            requestFile
        )

        val titleBody = title

        return api.updateEvent(token, eventId, title, location_name, description, start_time, end_time, category_id, imagePart)
    }

    suspend fun getCategories(): EventCategoriesResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.fetchCategories(token)
    }

    suspend fun deleteEvent(eventId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.deleteEvent(token, eventId)
    }

    suspend fun fetchEventDetails(eventId: Int): EventDetailsResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        return api.fetchEventDetails(token, eventId)
    }



}