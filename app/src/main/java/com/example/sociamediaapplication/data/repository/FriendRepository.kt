package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.SuggestedUsersResponse

class FriendRepository(
    private val tokenManager: TokenManager
) {
    val api = RetrofitClient.friendApi

    suspend fun getSuggestedUsers(): SuggestedUsersResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getSuggestedUsers(token)
    }
}