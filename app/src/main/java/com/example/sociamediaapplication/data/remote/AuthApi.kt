package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.AuthResponse
import com.example.sociamediaapplication.model.LoginRequest
import com.example.sociamediaapplication.model.SignupRequest
import retrofit2.http.Body
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
}
