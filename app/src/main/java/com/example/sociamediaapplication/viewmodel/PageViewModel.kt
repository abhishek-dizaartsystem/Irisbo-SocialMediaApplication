package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.repository.PageRepository
import com.example.sociamediaapplication.model.response.PageCategoriesResponse
import com.example.sociamediaapplication.model.response.PageFollowersResponse
import com.example.sociamediaapplication.model.response.PagePostsResponse
import com.example.sociamediaapplication.model.response.PagesResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _pagePosts = MutableStateFlow<PagePostsResponse?>(null)
    val pagePosts: StateFlow<PagePostsResponse?> = _pagePosts

    private val _createPageSuccess = MutableSharedFlow<Boolean>()
    val createPageSuccess: SharedFlow<Boolean> = _createPageSuccess

    private val _categories = MutableStateFlow<PageCategoriesResponse?>(null)
    val categories: StateFlow<PageCategoriesResponse?> = _categories


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

    fun loadPagePosts(pageId: Int){
        viewModelScope.launch {
            try {
                Log.d("PAGE_POSTS", "Fetching posts for pageId=$pageId")
                val response = repository.fetchPagePosts(pageId)
                Log.d("PAGE_POSTS", "Fetched ${response.data.size} posts, success=${response.success}")
                _pagePosts.value = response
            } catch (e: Exception) {
                Log.e("PAGE_POSTS", "Error fetching page posts: ${e.message}", e)
            }
        }
    }

    fun createPage(
        name: String,
        bio: String,
        profileImageUri: Uri,
        coverImageUri: Uri,
        website: String,
        phone: String,
        email: String,
        address: String,
        context: Context,
        categoryId: Int
    ) {
        viewModelScope.launch {
            try {
                repository.createPage(
                    name, bio, profileImageUri, coverImageUri,
                    website, phone, email, address, context, categoryId
                )
                _createPageSuccess.emit(true)
            } catch (e: Exception) {
                Log.e("CREATE_PAGE", "Error creating page: ${e.message}", e)
                _createPageSuccess.emit(false)
            }
        }
    }

    fun deletePage(pageId: Int) {
        viewModelScope.launch {
            try {
                repository.deletePage(pageId)
                loadPages()
            } catch (e: Exception) {
                Log.e("DELETE_PAGE", "Error deleting page: ${e.message}", e)
            }
        }
    }

    fun followPage(pageId: Int) {
        viewModelScope.launch {
            try {
                repository.followPage(pageId)
                loadPages()
                Log.d("FOLLOW_PAGE_SUCCESS", "Page followed successfully")
            } catch (e: Exception) {
                Log.e("FOLLOW_PAGE", "Error following page: ${e.message}", e)
            }
        }
    }

    fun unfollowPage(pageId: Int) {
        viewModelScope.launch {
            try {
                repository.unfollowPage(pageId)
                loadPages()
            } catch (e: Exception) {
                Log.e("UNFOLLOW_PAGE", "Error unfollowing page: ${e.message}", e)
            }
        }
    }

    fun loadCatgories(){
        viewModelScope.launch {
            try {
                _categories.value = repository.fetchCategoriesType()
            } catch (e: Exception){
                Log.e("FETCH_CATEGORIES", "Error fetching categories: ${e.message}", e)
            }
        }
    }

    fun updateCoverImage(uri: Uri){
        _coverPhoto.value = uri
    }

    fun updatePageProfile(uri: Uri){
        _pageProfile.value = uri
    }


}