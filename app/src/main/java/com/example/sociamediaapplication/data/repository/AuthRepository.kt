package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.AuthResponse
import com.example.sociamediaapplication.model.LoginRequest
import com.example.sociamediaapplication.model.SignupRequest

class AuthRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.api

    suspend fun signup(
        name: String,
        username: String,
        email: String,
        password: String,
    ): AuthResponse {

        val response = api.signup(SignupRequest(name, username, email, password))

        response.token?.let {
            tokenManager.saveToken(it)
        }

        return response
    }

    suspend fun login(
        email: String,
        password: String
    ): AuthResponse {

        val response = api.login(LoginRequest(email, password))

        response.token?.let {
            tokenManager.saveToken(it)
        }

        return response

    }

    suspend fun logout(): AuthResponse {
        val token = tokenManager.getToken()

        if (token == null) {
            tokenManager.clearToken()
            return AuthResponse("Already logged out", null)
        }

        val response = api.logout("Bearer $token")
        tokenManager.clearToken()
        return response
    }

    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }


}
