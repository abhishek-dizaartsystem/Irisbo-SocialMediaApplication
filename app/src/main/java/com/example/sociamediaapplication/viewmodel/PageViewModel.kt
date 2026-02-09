package com.example.sociamediaapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PageViewModel: ViewModel() {

    private val _coverPhoto = MutableStateFlow<Any>(R.drawable.coverphoto)
    val coverPhoto: StateFlow<Any> = _coverPhoto

    private val _pageProfile = MutableStateFlow<Any>(R.drawable.photoinitial)
    val pageProfile: MutableStateFlow<Any> = _pageProfile

    fun updateCoverImage(uri: Uri){
        _coverPhoto.value = uri
    }

    fun updatePageProfile(uri: Uri){
        _pageProfile.value = uri
    }


}