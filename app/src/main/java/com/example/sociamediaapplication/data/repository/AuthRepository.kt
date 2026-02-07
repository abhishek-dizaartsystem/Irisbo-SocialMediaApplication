package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.AuthResponse
import com.example.sociamediaapplication.model.LoginRequest
import com.example.sociamediaapplication.model.SignupRequest

class AuthRepository {

    private val api = RetrofitClient.api

    suspend fun signup(
        name: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): AuthResponse {

        return api.signup(SignupRequest(name, username, email, password))
    }

    suspend fun login(
        email: String,
        password: String
    ): AuthResponse {
        return api.login(LoginRequest(email, password))
    }
}
