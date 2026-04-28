package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.LoginRequest
import com.example.sociamediaapplication.model.request.SignupRequest
import com.example.sociamediaapplication.model.response.AuthResponse

class AuthRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.authApi

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

        println("Logout repository")
        val token = tokenManager.getToken()

        // If token already missing → just clear + return
        if (token.isNullOrEmpty()) {
            tokenManager.clearToken()

            return AuthResponse("Already logged out", null)
        }

        val response = api.logout("Bearer $token")
        tokenManager.clearToken()
        println("token cleared")
        return response
    }

    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }


}
