package com.example.sociamediaapplication.viewmodel

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object LoggedOut : AuthUiState()
    data class Success(val message: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()

}
