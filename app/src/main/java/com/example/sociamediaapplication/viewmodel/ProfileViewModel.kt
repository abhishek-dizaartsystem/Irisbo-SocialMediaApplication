package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.repository.ProfileRepository
import com.example.sociamediaapplication.model.request.EditProfileRequest
import com.example.sociamediaapplication.model.response.ProfileResponse
import com.example.sociamediaapplication.view.navigation.MainRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<ProfileResponse?>(null)
    val profile: StateFlow<ProfileResponse?> = _profile

    private val _otherProfile = MutableStateFlow<ProfileResponse?>(null)
    val otherProfile: StateFlow<ProfileResponse?> = _otherProfile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadProfile()
    }

//    private val _publicProfile = MutableStateFlow<ProfileResponse?>(null)
//    val publicProfile: StateFlow<ProfileResponse?> = _publicProfile

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

            Log.e("Profile_Debug", "init called")

            try {
                val response = repository.getProfile()

//                Log.e("Profile_debug", "id = ${response.data.id}")
//                Log.e("Profile_debug", "name = ${response.data.name}")
//                Log.e("Profile_debug", "username = ${response.data.username}")
//                Log.e("Profile_debug", "email = ${response.data.email}")
//                Log.e("Profile_debug", "bio = ${response.data.bio}")
//                Log.e("Profile_debug", "education = ${response.data.education}")
//                Log.e("Profile_debug", "work = ${response.data.work}")
//                Log.e("Profile_debug", "phone = ${response.data.phone}")
//                Log.e("Profile_debug", "profile_image = ${response.data.profile_image}")
//                Log.e("Profile_debug", "cover_img = ${response.data.cover_img}")
//                Log.e("Profile_debug", "create_at = ${response.data.create_at}")


                val fixedProfile = response.data.copy(
                    profile_image = buildImageUrl(response.data.profile_image),
                    cover_img = buildImageUrl(response.data.cover_img)
                )

//                Log.e("Profile_debug", response.data.profile_image?:"No value")

                _profile.value = response

//                Log.e("Profile_debug", profile.value?.data?.profile_image?.removePrefix("/").let {
//                    "${RetrofitClient.BASE_URL}$it"
//                })

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

    fun loadPublicProfile(userId: Int){
        viewModelScope.launch {
            try {
                _otherProfile.value = repository.getPublicProfile(userId)
            }catch (e: Exception){
                Log.e("ProfileViewModel", e.message.toString())
            }
        }
    }
}
