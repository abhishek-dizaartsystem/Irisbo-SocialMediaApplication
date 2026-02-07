package com.example.sociamediaapplication.model

data class SignupRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)
