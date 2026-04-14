package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.FriendStatusResponse
import com.example.sociamediaapplication.model.response.MyFriendsResponse
import com.example.sociamediaapplication.model.response.ReceivedRequestResponse
import com.example.sociamediaapplication.model.response.SuggestedUsersResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FriendApi {
    @GET("api/friends/suggestions")
    suspend fun getSuggestedUsers(
        @Header("Authorization") token: String
    ): SuggestedUsersResponse

    @POST("api/friends/request/{userId}")
    suspend fun sendFriendRequest(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    )

    @GET("api/friends/requests/received")
    suspend fun getReceivedRequests(
        @Header("Authorization") token: String
    ): ReceivedRequestResponse

    @GET("api/friends/requests/sent")
    suspend fun getSentRequests(
        @Header("Authorization") token: String
    ): ReceivedRequestResponse

    @GET("api/friends/status/{userId}")
    suspend fun getFriendshipStatus(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): FriendStatusResponse //Not implemented

    @GET("api/friends")
    suspend fun getMyFriends(
        @Header("Authorization") token: String
    ): MyFriendsResponse

    @PUT("api/friends/accept/{userId}")
    suspend fun acceptRequest(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    )

    @PUT("api/friends/reject/{userId}")
    suspend fun rejectRequest(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    )

    @DELETE("api/friends/{userId}")
    suspend fun unfriendUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    )

    @DELETE("api/friends/request/{userId}")
    suspend fun cancelRequest(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    )
}