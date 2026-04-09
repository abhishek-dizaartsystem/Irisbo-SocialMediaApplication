package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.SuggestedUsersResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface FriendApi {
    @GET("api/friends/suggestions")
    suspend fun getSuggestedUsers(
        @Header("Authorization") token: String
    ): SuggestedUsersResponse
}