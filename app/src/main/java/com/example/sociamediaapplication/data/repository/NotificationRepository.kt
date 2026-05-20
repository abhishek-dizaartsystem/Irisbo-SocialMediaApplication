package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.RemoveFcmTokenRequest
import com.example.sociamediaapplication.model.request.SaveFcmTokenRequest

class NotificationRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.notificationApi

    suspend fun saveFcmToken(
        deviceId: String,
        fcmToken: String
    ) {

        val token =
            "Bearer ${tokenManager.getToken()}"

        api.saveFcmToken(
            token,
            SaveFcmTokenRequest(
                device_id = deviceId,
                fcm_token = fcmToken
            )
        )
    }

    suspend fun removeFcmToken(
        deviceId: String
    ) {

        val token =
            "Bearer ${tokenManager.getToken()}"

        api.removeFcmToken(
            token,
            RemoveFcmTokenRequest(deviceId)
        )
    }
}