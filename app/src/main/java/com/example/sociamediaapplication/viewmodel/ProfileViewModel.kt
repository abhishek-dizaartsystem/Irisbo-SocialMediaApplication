package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.repository.ProfileRepository
import com.example.sociamediaapplication.model.request.EditProfileRequest
import com.example.sociamediaapplication.model.response.ProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<ProfileResponse?>(null)
    val profile: StateFlow<ProfileResponse?> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadProfile()
    }

    /**
     * Converts backend image path to full usable URL
     * Handles:
     * 1️⃣ Full URL already returned
     * 2️⃣ Relative path (/uploads/img.jpg)
     * 3️⃣ Null safety
     * 4️⃣ Cache busting
     */
    private fun buildImageUrl(path: String?): String? {
        if (path.isNullOrBlank()) return null

        val timestamp = System.currentTimeMillis()

        return if (path.startsWith("http")) {
            "$path?t=$timestamp"
        } else {
            "${RetrofitClient.BASE_URL}$path?t=$timestamp"
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = repository.getProfile()

                val fixedProfile = response.copy(
                    profile_img = buildImageUrl(response.profile_img),
                    cover_img = buildImageUrl(response.cover_img)
                )

                _profile.value = fixedProfile

            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Failed to load profile"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun editProfile(
        request: EditProfileRequest,
        context: Context,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {

                repository.editProfile(request, context)

                loadProfile()   // refresh profile
                onSuccess()

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun uploadProfileImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.uploadProfileImage(uri, context)

                // Reload after upload so UI refreshes
                loadProfile()

            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Profile upload failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadCoverImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.uploadCoverImage(uri, context)

                // Reload after upload so UI refreshes
                loadProfile()

            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Cover upload failed"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
