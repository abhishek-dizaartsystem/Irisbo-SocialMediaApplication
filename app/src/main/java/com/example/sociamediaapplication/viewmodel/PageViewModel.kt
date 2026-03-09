package com.example.sociamediaapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.repository.PageRepository
import com.example.sociamediaapplication.model.response.PageFollowersResponse
import com.example.sociamediaapplication.model.response.PagesResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PageViewModel(
    private val repository: PageRepository
): ViewModel() {

    private val _coverPhoto = MutableStateFlow<Any>(R.drawable.coverphoto)
    val coverPhoto: StateFlow<Any> = _coverPhoto

    private val _pageProfile = MutableStateFlow<Any>(R.drawable.photoinitial)
    val pageProfile: MutableStateFlow<Any> = _pageProfile

    private val _pages = MutableStateFlow<PagesResponse?>(null)
    val pages: StateFlow<PagesResponse?> = _pages

    private val _pageFollowers = MutableStateFlow<PageFollowersResponse?>(null)
    val pageFollowers: StateFlow<PageFollowersResponse?> = _pageFollowers

    fun loadPages(){
        viewModelScope.launch {
            val pages = repository.fetchPages()
            _pages.value = pages
        }

    }

    fun loadPageFollowers(pageId: Int){
        viewModelScope.launch {
            val response = repository.fetchPageFollowers(pageId)
            _pageFollowers.value = response
        }
    }

    fun updateCoverImage(uri: Uri){
        _coverPhoto.value = uri
    }

    fun updatePageProfile(uri: Uri){
        _pageProfile.value = uri
    }


}