package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.PostRepository
import com.example.sociamediaapplication.model.response.PostResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostResponse>>(emptyList())
    val posts: StateFlow<List<PostResponse>> = _posts

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _posts.value = repository.getPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }

    fun createPost(caption: String, uris: List<Uri>, context: Context) {
        viewModelScope.launch {
            try {
                repository.createPost(caption, uris, context)

                // 🔥 refresh feed
                loadPosts()

            } catch (e: Exception) {
                println("POST ERROR: ${e.message}")
            }
        }
    }

}
