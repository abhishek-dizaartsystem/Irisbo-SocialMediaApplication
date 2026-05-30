package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.VideoRepository
import com.example.sociamediaapplication.model.response.GetMyVideosResponse
import com.example.sociamediaapplication.model.response.GetVideosResponse
import com.example.sociamediaapplication.model.response.RelatedVideosResponse
import com.example.sociamediaapplication.model.response.SingleVideoResponse
import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val repository: VideoRepository
): ViewModel() {

    private val _categories = MutableStateFlow<VideoCategoryResponse?>(null)
    val categories: StateFlow<VideoCategoryResponse?> = _categories

    private val _videos = MutableStateFlow<GetVideosResponse?>(null)
    val videos: StateFlow<GetVideosResponse?> = _videos

    private val _searchSuggestions =
        MutableStateFlow<List<String>>(emptyList())

    val searchSuggestions: StateFlow<List<String>>
            = _searchSuggestions

    private val _myVideos = MutableStateFlow<GetMyVideosResponse?>(null)
    val myVideos: StateFlow<GetMyVideosResponse?> = _myVideos

    private val _video = MutableStateFlow<SingleVideoResponse?>(null)
    val video: StateFlow<SingleVideoResponse?> = _video

    // VideoViewModel.kt
    private val _isFullscreen = MutableStateFlow(false)
    val isFullscreen: StateFlow<Boolean> = _isFullscreen

    private val _relatedVideos = MutableStateFlow<RelatedVideosResponse?>(null)
    val relatedVideos: StateFlow<RelatedVideosResponse?> = _relatedVideos

    fun setFullscreen(value: Boolean) {
        _isFullscreen.value = value
    }

    fun searchVideos(value: String){
        viewModelScope.launch {
            try {
                val response = repository.searchVideos(value)

                _videos.value = response

                _searchSuggestions.value =
                    response.videos.map { it.title }
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }

    fun fetchVideoCategories(){
        viewModelScope.launch {
            try {
                _categories.value = repository.getVideoCategories()
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }

    fun fetchMyVideos(){
        viewModelScope.launch {
            try {
                _myVideos.value = repository.getMyVideos()
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }

    fun fetchVideosByCategory(categoryId: Int){
        viewModelScope.launch {
            try {
                val response = repository.getVideosByCategory(categoryId)
                Log.d("VideoVM_DEBUG", response.toString())

                _videos.value = response

                _searchSuggestions.value =
                    response.videos.map { it.title }
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }

    fun fetchAllVideos(){
        viewModelScope.launch {
            try {
                val response = repository.getAllVideos()
                Log.d("VideoVM_DEBUG", response.toString())

                _videos.value = response

                _searchSuggestions.value =
                    response.videos.map { it.title }
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }

    fun fetchVideo(videoId: Int){
        viewModelScope.launch {
            try {
                val response = repository.getVideo(videoId)

                _video.value = response
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }

    fun fetchRelatedVideos(videoId: Int){
        viewModelScope.launch {
            try {
                val response = repository.getRelatedVideos(videoId)
                Log.d("VideoVM_DEBUG", response.toString())

                _relatedVideos.value = response
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }

    fun toggleLike(videoId: Int) {

        val currentVideo = _video.value ?: return
        val currentData = currentVideo.data

        val currentReaction = currentData.viewer_reaction

        // 🔥 OPTIMISTIC UPDATE
        val updatedData = when (currentReaction) {

            "like" -> {

                currentData.copy(
                    viewer_reaction = null,
                    likes_count = (currentData.likes_count - 1).coerceAtLeast(0)
                )
            }

            "dislike" -> {

                currentData.copy(
                    viewer_reaction = "like",
                    likes_count = currentData.likes_count + 1,
                    dislikes_count = (currentData.dislikes_count - 1)
                        .coerceAtLeast(0)
                )
            }

            else -> {

                currentData.copy(
                    viewer_reaction = "like",
                    likes_count = currentData.likes_count + 1
                )
            }
        }

        _video.value = currentVideo.copy(
            data = updatedData
        )

        // 🔥 API CALL
        viewModelScope.launch {

            try {

                when (currentReaction) {

                    "like" -> {
                        repository.removeVideoReaction(videoId)
                    }

                    else -> {
                        repository.likeVideo(videoId)
                    }
                }

            } catch (e: Exception) {

                // 🔥 ROLLBACK
                _video.value = currentVideo

                Log.e(
                    "VideoVM_DEBUG",
                    e.message.toString()
                )
            }
        }
    }

    fun toggleDislike(videoId: Int) {

        val currentVideo = _video.value ?: return
        val currentData = currentVideo.data

        val currentReaction = currentData.viewer_reaction

        // 🔥 OPTIMISTIC UPDATE
        val updatedData = when (currentReaction) {

            "dislike" -> {

                currentData.copy(
                    viewer_reaction = null,
                    dislikes_count =
                        (currentData.dislikes_count - 1)
                            .coerceAtLeast(0)
                )
            }

            "like" -> {

                currentData.copy(
                    viewer_reaction = "dislike",
                    dislikes_count =
                        currentData.dislikes_count + 1,

                    likes_count =
                        (currentData.likes_count - 1)
                            .coerceAtLeast(0)
                )
            }

            else -> {

                currentData.copy(
                    viewer_reaction = "dislike",
                    dislikes_count =
                        currentData.dislikes_count + 1
                )
            }
        }

        _video.value = currentVideo.copy(
            data = updatedData
        )

        // 🔥 API CALL
        viewModelScope.launch {

            try {

                when (currentReaction) {

                    "dislike" -> {
                        repository.removeVideoReaction(videoId)
                    }

                    else -> {
                        repository.dislikeVideo(videoId)
                    }
                }

            } catch (e: Exception) {

                // 🔥 ROLLBACK
                _video.value = currentVideo

                Log.e(
                    "VideoVM_DEBUG",
                    e.message.toString()
                )
            }
        }
    }

    fun toggleSave(videoId: Int){
        val currentVideo = _video.value ?: return
        val currentData = currentVideo.data

        val isSaved = currentData.is_saved

        val updatedData = when(isSaved){
            0->{
                currentData.copy(
                    is_saved = 1
                )
            }

            1->{
                currentData.copy(
                    is_saved = 0
                )
            }

            else -> {
                //To Avoid compile errors
                currentData
            }
        }

        _video.value = currentVideo.copy(
            data = updatedData
        )

        viewModelScope.launch {
            try {
                when(isSaved){
                    0->{
                        repository.saveVideo(videoId)
                    }
                    1->{
                        repository.unsaveVideo(videoId)
                    }
                }
            }catch (e: Exception) {

                // 🔥 ROLLBACK
                _video.value = currentVideo

                Log.e(
                    "VideoVM_DEBUG",
                    e.message.toString()
                )
            }
        }
    }
}