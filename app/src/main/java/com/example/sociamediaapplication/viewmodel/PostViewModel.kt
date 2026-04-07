package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
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
//        _posts.value = _posts.value.map {
//            if(it.id == post.id) {
//                it.copy(
//                    is_saved = if(it.is_saved == 1) 0 else 1
//                )
//            }else it
//        }
        viewModelScope.launch {
            try {
                val response = if(post.is_saved) repository.unsavePost(post.id) else repository.savePost(post.id)

                _posts.value = _posts.value.map {
                    if(it.id == post.id) {
                        it.copy(
                            is_saved = if(response.message.contains("unsaved successfully")) false else true
                        )
                    }else it
                }
            }catch (e: Exception){
                loadPosts(id)
            }
        }
    }

    fun toggleLike(post: PostResponse, id: Int){
        val currentlyLiked = post.user_reaction == "like"

        // 🔥 Optimistic UI update (instant feedback)
        _posts.value = _posts.value.map {
            if (it.id == post.id) {
                it.copy(
                    user_reaction = if (currentlyLiked) "dislike" else "like",
                    likes_count = if (currentlyLiked) it.likes_count - 1 else it.likes_count + 1
                )
            } else it
        }
        viewModelScope.launch {
            try {
                val response = if (currentlyLiked) {
                    repository.unlikePost(post.id)
                } else {
                    repository.likePost(post.id)
                }
                Log.e("Post_like", response.toString())
                Log.e("Post_like", response.data.user_reaction.reaction)
                // ✅ Sync with backend truth
                _posts.value = _posts.value.map {
                    if (it.id == post.id) {
                        it.copy(
                            user_reaction = response.data.user_reaction.reaction,
                            likes_count = response.data.counts.likes
                        )

                    } else it
                }

            } catch (e: Exception) {
                // 🔥 rollback on failure
                loadPosts(id)
            }
        }
    }

//    fun createPost(
//        caption: String,
//        mediaUris: List<Uri>?,
//        context: Context,
//        id:Int,
//        onSuccess: () -> Unit
//    ) {
//        viewModelScope.launch {
//            _isUploading.value = true
//            try {
//
//                repository.createPost(
//                    captionText = caption,
//                    uris = mediaUris,
//                    context = context
//                )
//
//                // 🔥 reload feed after upload
//                repository.getPosts(id)
//
//                onSuccess()
//
//            } catch (e: Exception) {
//                _error.value = e.message
//            } finally {
//                _isUploading.value = false
//            }
//        }
//    }


}

