package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.EventCategoriesResponse
import com.example.sociamediaapplication.model.response.EventDetailsResponse
import com.example.sociamediaapplication.model.response.EventsResponse
import com.example.sociamediaapplication.model.response.MyEventsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface EventApi {
    @GET("api/events")
    suspend fun fetchEvents(
        @Header("Authorization") token: String
    ): EventsResponse

    @GET("api/events/my")
    suspend fun fetchMyEvents(
        @Header("Authorization") token: String
    ): MyEventsResponse

    @GET("api/events/{eventId}")
    suspend fun fetchEventDetails(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Int
    ): EventDetailsResponse

    @Multipart
    @POST("api/events")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part("location_name") location_name: RequestBody,
        @Part("description")description: RequestBody,
        @Part("start_time") start_time: RequestBody,
        @Part("end_time") end_time: RequestBody,
        @Part("category_id") category_id: RequestBody,
        @Part cover_image: MultipartBody.Part,
    )

    @DELETE("api/events/{eventId}")
    suspend fun deleteEvent(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Int
    )

    @PATCH("api/events/{eventId}")
    suspend fun updateEvent(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Int,
        @Part("title") title: RequestBody,
        @Part("location_name") location_name: RequestBody,
        @Part("description")description: RequestBody,
        @Part("start_time") start_time: RequestBody,
        @Part("end_time") end_time: RequestBody,
        @Part("category_id") category_id: RequestBody,
        @Part cover_image: MultipartBody.Part,
    )

    @GET("api/events/categories")
    suspend fun fetchCategories(
        @Header("Authorization") token: String
    ): EventCategoriesResponse

}