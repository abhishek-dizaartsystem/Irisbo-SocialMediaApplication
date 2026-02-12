package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.ProfileResponse

class ProfileRepository(
    private val tokenManager: TokenManager
) {
    private val api = RetrofitClient.profileApi

    suspend fun getProfile(): ProfileResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token found")

        return api.getProfile("Bearer $token")
    }
}