package com.example.sociamediaapplication.model.request

data class SaveFcmTokenRequest(
    val device_id: String,
    val fcm_token: String,
    val platform: String = "android"
)
