package com.example.sociamediaapplication.viewmodel

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.ProfileRepository
import com.example.sociamediaapplication.model.response.ProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
): ViewModel() {

    private val _profile =
        MutableStateFlow<ProfileResponse?>(
            ProfileResponse(
                "john_doe",
                "John Doe",
                "Carpediem",
                "https://picsum.photos/200/300",
                "https://picsum.photos/200/300"
            )
        )

    val profile: StateFlow<ProfileResponse?> = _profile

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val response = repository.getProfile()
                _profile.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Username
    private val _userUsername = MutableStateFlow<String>(_profile.value?.userName?:"john_doe")
    val userUsername: StateFlow<String> = _userUsername

    //Name
    private val _userName = MutableStateFlow<String>(_profile.value?.name?:"John Doe")
    val userName: StateFlow<String> = _userName

    fun updateUserName(newName: String){
        _userName.value = newName
    }

    //Bio
    private val _userBio = MutableStateFlow<String>(_profile.value?.bio?:"Carpediem")
    val userBio: StateFlow<String> = _userBio

    fun updateUserBio(newBio: String){
        _userBio.value = newBio
    }

    //Profile Pic
    private val _userProfileImage = MutableStateFlow<Uri>((_profile.value?.profileImageUrl?:"https://picsum.photos/200/300").toUri())
    val userProfileImage: StateFlow<Uri> = _userProfileImage

    fun updateUserProfileImage(uri: Uri){
        _userProfileImage.value = uri
    }

    //Cover Pic
    private val _userCoverImage = MutableStateFlow<Uri>((_profile.value?.coverImageUrl?:"https://picsum.photos/200/300").toUri())
    val userCoverImage: StateFlow<Uri> = _userCoverImage

    fun updateUserCoverImage(uri: Uri){
        _userCoverImage.value = uri
    }

    //Posts
    private val _userPosts = MutableStateFlow<List<Uri>>(
        List<Uri>(8){ "https://picsum.photos/200/300".toUri() }
    )
    val userPosts: StateFlow<List<Uri>> = _userPosts

    fun addPost(uri: Uri){
        _userPosts.update { current->
            current + uri
        }
    }
    fun removePost(uri: Uri){
        _userPosts.update { current->
            current - uri
        }
    }

    //Reels
    private val _userReels = MutableStateFlow<List<Uri>>(
        List<Uri>(8){ "https://picsum.photos/200/300".toUri() }
    )
    val userReels: StateFlow<List<Uri>> = _userReels

    fun addReel(uri: Uri){
        _userReels.update { current->
            current + uri
        }
    }
    fun removeReel(uri: Uri){
        _userReels.update { current->
            current - uri
        }
    }

    //Update Profile(EditProfile)
    fun updateProfile(){

    }
}