package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.FriendStatusResponse
import com.example.sociamediaapplication.model.response.MyFriendsResponse
import com.example.sociamediaapplication.model.response.ReceivedRequestResponse
import com.example.sociamediaapplication.model.response.SuggestedUsersResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

class FriendRepository(
    private val tokenManager: TokenManager
) {
    val api = RetrofitClient.friendApi

    suspend fun getSuggestedUsers(): SuggestedUsersResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getSuggestedUsers(token)
    }

    suspend fun sendFriendRequest(userId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        return api.sendFriendRequest(token, userId)
    }

    suspend fun getReceivedRequests(): ReceivedRequestResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getReceivedRequests(token)
    }

    suspend fun getSentRequests(): ReceivedRequestResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getSentRequests(token)
    }

    suspend fun getFriendshipStatus(userId: Int): FriendStatusResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getFriendshipStatus(token, userId)
    }

    suspend fun getMyFriends(): MyFriendsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMyFriends(token)
    }

}