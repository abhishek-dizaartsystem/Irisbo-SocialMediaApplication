package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.PagesResponse

class PageRepository(
    private val tokenManager: TokenManager
) {
    private val api = RetrofitClient.pageApi

    suspend fun fetchPages(): PagesResponse{
        val token = "Bearer ${tokenManager.getToken()}"
        val response = api.fetchPages(token)

        return response
    }
}