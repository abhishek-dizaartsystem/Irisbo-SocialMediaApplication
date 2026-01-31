package com.example.sociamediaapplication.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.AuthRepository
import com.example.sociamediaapplication.model.AuthResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState: StateFlow<AuthResponse?> = _authState

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.signup(name, email, password)
                _authState.value = response   // 🔴 REQUIRED
            } catch (e: Exception) {
                _authState.value = AuthResponse(
                    message = e.message ?: "Error",
                    token = null
                )
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _authState.value = response   // 🔴 REQUIRED
            } catch (e: Exception) {
                _authState.value = AuthResponse(
                    message = e.message ?: "Error",
                    token = null
                )
            }
        }
    }
}
