package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState =
        MutableStateFlow<AuthUiState>(AuthUiState.Idle)

    val authState: StateFlow<AuthUiState> = _authState


    // -----------------------------
    // SIGNUP
    // -----------------------------
    fun signup(
        name: String,
        username: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _authState.value = AuthUiState.Loading

                val response =
                    repository.signup(name, username, email, password)

                _authState.value =
                    AuthUiState.Success(response.message?:"")

            } catch (e: Exception) {
                _authState.value =
                    AuthUiState.Error(e.message ?: "Signup failed")
            }
        }
    }


    // -----------------------------
    // LOGIN
    // -----------------------------
    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _authState.value = AuthUiState.Loading

                val response =
                    repository.login(email, password)

                _authState.value =
                    AuthUiState.Success(response.message?:"")

            } catch (e: Exception) {
                _authState.value =
                    AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }


    // -----------------------------
    // LOGOUT
    // -----------------------------
    fun logout() {
        viewModelScope.launch {
            try {
                _authState.value = AuthUiState.Loading

                println("Logout Clicked")
                val response = repository.logout()

                println("Logout Called")

                Log.d("AUTH_DEBUG", "${response.message}")
                Log.d("AUTH_DEBUG", "${response.token}")

                _authState.value =
                    AuthUiState.Success(response.message?:"")

            } catch (e: Exception) {
                Log.e("AuthVM_DEBUG", e.message.toString())
                _authState.value =
                    AuthUiState.Error(e.message ?: "Logout failed")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthUiState.Idle
    }
}
