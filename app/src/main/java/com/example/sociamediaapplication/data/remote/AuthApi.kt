package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.AuthResponse
import com.example.sociamediaapplication.model.request.LoginRequest
import com.example.sociamediaapplication.model.request.SignupRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): AuthResponse

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("api/auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): AuthResponse
}
