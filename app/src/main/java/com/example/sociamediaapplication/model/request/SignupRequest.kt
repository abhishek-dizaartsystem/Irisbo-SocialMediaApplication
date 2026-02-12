package com.example.sociamediaapplication.model.request

data class SignupRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)
