package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.RemoveFcmTokenRequest
import com.example.sociamediaapplication.model.request.SaveFcmTokenRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface NotificationApi {
    @POST("api/notifications/fcm-token")
    suspend fun saveFcmToken(
        @Header("Authorization") token: String,
        @Body body: SaveFcmTokenRequest
    )

    @DELETE("api/notifications/fcm-token")
    suspend fun removeFcmToken(
        @Header("Authorization") token: String,
        @Body body: RemoveFcmTokenRequest
    )
}