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

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        //loadPosts()
    }

    fun loadPosts(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _posts.value = repository.getPosts(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }

    fun toggleSave(post: PostResponse, id: Int){
        _posts.value = _posts.value.map {
            if(it.id == post.id) {
                it.copy(
                    is_saved = if(it.is_saved == 1) 0 else 1
                )
            }else it
        }
        viewModelScope.launch {
            try {
                val response = repository.toggleSave(post.id)

                _posts.value = _posts.value.map {
                    if(it.id == post.id) {
                        it.copy(
                            is_saved = if(response.saved)1 else 0
                        )
                    }else it
                }
            }catch (e: Exception){
                loadPosts(id)
            }
        }
    }

    fun toggleLike(post: PostResponse, id: Int){
        _posts.value = _posts.value.map{
            if(it.id == post.id){
                it.copy(
                    is_liked = if(it.is_liked == 1) 0 else 1,
                    likes_count =
                        if (it.is_liked == 1) it.likes_count - 1
                        else it.likes_count + 1
                )
            }else it
        }
        viewModelScope.launch {
            try {
                val response = repository.toggleLike(post.id)

                // 🔥 sync with backend truth
                _posts.value = _posts.value.map {
                    if (it.id == post.id) {
                        it.copy(
                            is_liked = if(response.liked) 1 else 0,
                            likes_count = response.likes_count
                        )
                    } else it
                }

            } catch (e: Exception) {
                // 🔥 rollback on failure
                loadPosts(id)
            }
        }
    }

    fun createPost(
        caption: String,
        mediaUris: List<Uri>?,
        context: Context,
        id:Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isUploading.value = true
            try {

                repository.createPost(
                    captionText = caption,
                    uris = mediaUris,
                    context = context
                )

                // 🔥 reload feed after upload
                repository.getPosts(id)

                onSuccess()

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isUploading.value = false
            }
        }
    }


}

