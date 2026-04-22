package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.StoryRepository
import com.example.sociamediaapplication.model.response.FriendStoriesResponse
import com.example.sociamediaapplication.model.response.MyStoriesResponse
import com.example.sociamediaapplication.model.response.SingleUserStoryResponse
import com.example.sociamediaapplication.model.response.StoryViewerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class StoryViewModel(
    private val repository: StoryRepository
): ViewModel() {

    private val _stories = MutableStateFlow<FriendStoriesResponse?>(null)
    val stories: StateFlow<FriendStoriesResponse?> = _stories

    private val _myStories = MutableStateFlow<MyStoriesResponse?>(null)
    val myStories: StateFlow<MyStoriesResponse?> = _myStories

    private val _storyViewers = MutableStateFlow<StoryViewerResponse?>(null)
    val storyViewers: StateFlow<StoryViewerResponse?> = _storyViewers

    private val _userStories = MutableStateFlow<SingleUserStoryResponse?>(null)
    val userStories: StateFlow<SingleUserStoryResponse?> = _userStories

    fun getFriendsStories(){

        viewModelScope.launch {
            try {
                _stories.value = repository.getFriendsStories()
            }catch (e: Exception){
                Log.e("StoryViewModel", "get friends stories" + e.message.toString())
            }
        }
    }

    fun createStory(
        context: Context,
        imageUri: Uri,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                repository.createStory(context, imageUri)

                // 🔥 refresh data after upload
                getMyStories()
                getFriendsStories()

                onSuccess()

            } catch (e: Exception) {
                Log.e("StoryViewModel", "create story " + e.message.toString())
            }
        }
    }

    fun getMyStories(){

        viewModelScope.launch {
            try {
                _myStories.value = repository.getMyStories()
                Log.d("StoryViewModel", "get my stories = ${_myStories.value}")
            }catch (e: Exception){
                Log.e("StoryViewModel", "get my stories" + e.message.toString())
            }
        }
    }

    fun getSingleUserStories(userId: Int){

        viewModelScope.launch {
            try {
                _userStories.value = repository.getSingleUserStories(userId)
                Log.d("StoryViewModel", "get single user stories = ${_userStories.value}")

            }catch (e: Exception){
                Log.e("StoryViewModel", "get single user stories" + e.message.toString())
            }
        }
    }



    fun markStoryViewed(storyId: Int){
        viewModelScope.launch {
            try {
                repository.markStoryViewed(storyId)
                Log.d("StoryViewModel", "marked viewed = $storyId")
            }catch (e: Exception){
                Log.e("StoryViewModel", "mark story viewed" + e.message.toString())
            }
        }
    }

    fun getStoryViewers(storyId: Int){

        viewModelScope.launch {
            try {
                _storyViewers.value = repository.getStoryViewers(storyId)
            }catch (e: Exception){
                Log.e("StoryViewModel", "get story viewers" + e.message.toString())
            }
        }
    }

    fun deleteStory(storyId: Int){
        viewModelScope.launch {
            try {
                repository.deleteStory(storyId)
            }catch (e: Exception){
                Log.e("StoryViewModel", "delete story" + e.message.toString())
            }
        }
    }
}