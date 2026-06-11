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

import com.example.sociamediaapplication.model.response.VideoCommentsResponse
import com.example.sociamediaapplication.model.response.VideoComment
import kotlinx.coroutines.flow.asStateFlow

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostResponse>>(emptyList())
    val posts: StateFlow<List<PostResponse>> = _posts

    private val _otherProfilePosts = MutableStateFlow<List<PostResponse>>(emptyList())
    val otherProfilePosts: StateFlow<List<PostResponse>> = _otherProfilePosts

    private val _globalPosts = MutableStateFlow<List<PostResponse>>(emptyList())
    val globalPosts: StateFlow<List<PostResponse>> = _globalPosts


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

    fun loadOthersPosts(id: Int){
        viewModelScope.launch {
            _loading.value = true
            try {
                _otherProfilePosts.value = repository.getPosts(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadGlobalPosts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _globalPosts.value = repository.getGlobalPosts().posts
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

    fun toggleGlobalSave(post: PostResponse) {
        viewModelScope.launch {
            try {
                val response = if(post.is_saved) repository.unsavePost(post.id) else repository.savePost(post.id)

                _globalPosts.value = _globalPosts.value.map {
                    if(it.id == post.id) {
                        it.copy(
                            is_saved = if(response.message.contains("unsaved successfully")) false else true
                        )
                    } else it
                }
            } catch (e: Exception) {
                loadGlobalPosts()
            }
        }
    }

    fun toggleGlobalLike(post: PostResponse) {
        val currentlyLiked = post.user_reaction == "like"

        _globalPosts.value = _globalPosts.value.map {
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
                
                _globalPosts.value = _globalPosts.value.map {
                    if (it.id == post.id) {
                        it.copy(
                            user_reaction = response.data.user_reaction.reaction,
                            likes_count = response.data.counts.likes
                        )
                    } else it
                }

            } catch (e: Exception) {
                loadGlobalPosts()
            }
        }
    }

    fun toggleOtherProfilePostLike(post: PostResponse, id: Int){
        val currentlyLiked = post.user_reaction == "like"

        _otherProfilePosts.value = _otherProfilePosts.value.map {
            if(it.id == post.id){
                it.copy(
                    user_reaction = if (currentlyLiked) "dislike" else "like",
                    likes_count = if (currentlyLiked) it.likes_count - 1 else it.likes_count + 1
                )
            }else it
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
                _otherProfilePosts.value = _otherProfilePosts.value.map {
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

    fun toggleOtherProfileSave(post: PostResponse, id: Int){
        viewModelScope.launch {
            try {
                val response = if(post.is_saved) repository.unsavePost(post.id) else repository.savePost(post.id)

                _otherProfilePosts.value = _otherProfilePosts.value.map {
                    if(it.id == post.id) {
                        it.copy(
                            is_saved = if(response.message.contains("unsaved successfully")) false else true
                        )
                    }else it
                }
            } catch (e: Exception) {
                loadPosts(id)
            }
        }
    }

    private val _postComments = MutableStateFlow<VideoCommentsResponse?>(null)
    val postComments = _postComments.asStateFlow()

    fun fetchPostComments(postId: Int) {
        viewModelScope.launch {
            try {
                _postComments.value = repository.fetchComments(postId)
            } catch (e: Exception) {
                Log.e("PostViewModel_DEBUG", e.message.toString())
            }
        }
    }

    fun commentOnPost(postId: Int, content: String, parentId: Int? = null) {
        viewModelScope.launch {
            try {
                repository.commentOnPost(postId, content, parentId)
                fetchPostComments(postId)
            } catch (e: Exception) {
                Log.e("PostViewModel_DEBUG", e.message.toString())
            }
        }
    }

    private fun findComment(comments: List<VideoComment>, commentId: Int): VideoComment? {
        for (comment in comments) {
            if (comment.id == commentId) return comment
            val found = findComment(comment.replies, commentId)
            if (found != null) return found
        }
        return null
    }

    private fun updateCommentLike(comments: List<VideoComment>, commentId: Int): List<VideoComment> {
        return comments.map { comment ->
            if (comment.id == commentId) {
                when (comment.user_reaction) {
                    "like" -> {
                        comment.copy(
                            user_reaction = null,
                            likes = (comment.likes - 1).coerceAtLeast(0)
                        )
                    }
                    "dislike" -> {
                        comment.copy(
                            user_reaction = "like",
                            likes = comment.likes + 1,
                            dislikes = (comment.dislikes - 1).coerceAtLeast(0)
                        )
                    }
                    else -> {
                        comment.copy(
                            user_reaction = "like",
                            likes = comment.likes + 1
                        )
                    }
                }
            } else {
                comment.copy(replies = updateCommentLike(comment.replies, commentId))
            }
        }
    }

    private fun updateCommentDislike(comments: List<VideoComment>, commentId: Int): List<VideoComment> {
        return comments.map { comment ->
            if (comment.id == commentId) {
                when (comment.user_reaction) {
                    "dislike" -> {
                        comment.copy(
                            user_reaction = null,
                            dislikes = (comment.dislikes - 1).coerceAtLeast(0)
                        )
                    }
                    "like" -> {
                        comment.copy(
                            user_reaction = "dislike",
                            dislikes = comment.dislikes + 1,
                            likes = (comment.likes - 1).coerceAtLeast(0)
                        )
                    }
                    else -> {
                        comment.copy(
                            user_reaction = "dislike",
                            dislikes = comment.dislikes + 1
                        )
                    }
                }
            } else {
                comment.copy(replies = updateCommentDislike(comment.replies, commentId))
            }
        }
    }

    fun toggleCommentLike(postId: Int, commentId: Int) {
        val currentComments = _postComments.value ?: return
        val oldComments = currentComments.comments
        val updatedComments = updateCommentLike(oldComments, commentId)

        _postComments.value = currentComments.copy(comments = updatedComments)

        val previousComment = findComment(oldComments, commentId) ?: return

        viewModelScope.launch {
            try {
                when (previousComment.user_reaction) {
                    "like" -> repository.removeCommentReaction(commentId)
                    else -> repository.likeComment(commentId)
                }
            } catch (e: Exception) {
                _postComments.value = currentComments
                Log.e("PostViewModel_DEBUG", e.message.toString())
            }
        }
    }

    fun toggleCommentDislike(postId: Int, commentId: Int) {
        val currentComments = _postComments.value ?: return
        val oldComments = currentComments.comments
        val updatedComments = updateCommentDislike(oldComments, commentId)

        _postComments.value = currentComments.copy(comments = updatedComments)

        val previousComment = findComment(oldComments, commentId) ?: return

        viewModelScope.launch {
            try {
                when (previousComment.user_reaction) {
                    "dislike" -> repository.removeCommentReaction(commentId)
                    else -> repository.dislikeComment(commentId)
                }
            } catch (e: Exception) {
                _postComments.value = currentComments
                Log.e("PostViewModel_DEBUG", e.message.toString())
            }
        }
    }
}

