package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.PageFollowersResponse
import com.example.sociamediaapplication.model.response.PagePostsResponse
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

    suspend fun fetchPageFollowers(pageId: Int): PageFollowersResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchPageFollowers(token, pageId)
    }

    suspend fun fetchPagePosts(pageId: Int): PagePostsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchPagePosts(token, pageId)
    }
}