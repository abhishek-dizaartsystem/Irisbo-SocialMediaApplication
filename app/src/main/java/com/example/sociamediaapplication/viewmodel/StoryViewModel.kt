package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.StoryRepository
import com.example.sociamediaapplication.model.response.FriendStoriesResponse
import com.example.sociamediaapplication.model.response.MyStoriesResponse
import com.example.sociamediaapplication.model.response.SingleUserStoryResponse
import com.example.sociamediaapplication.model.response.StoryViewerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class StoryViewModel(
    private val repository: StoryRepository
): ViewModel() {

    fun getFriendsStories(){

        viewModelScope.launch {
            try {
                repository.getFriendsStories()
            }catch (e: Exception){
                Log.e("StoryViewModel", e.message.toString())
            }
        }
    }

    fun createStory(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            try {
                repository.createStory(context, imageUri)
            }catch (e: Exception){
                    Log.e("StoryViewModel", e.message.toString())
                }
            }
    }

    fun getMyStories(){

        viewModelScope.launch {
            try {
                repository.getMyStories()
            }catch (e: Exception){
                Log.e("StoryViewModel", e.message.toString())
            }
        }
    }

    fun getSingleUserStories(userId: Int){

        viewModelScope.launch {
            try {
                repository.getSingleUserStories(userId)
            }catch (e: Exception){
                Log.e("StoryViewModel", e.message.toString())
            }
        }
    }



    fun markStoryViewed(storyId: Int){
        viewModelScope.launch {
            try {
                repository.markStoryViewed(storyId)
            }catch (e: Exception){
                Log.e("StoryViewModel", e.message.toString())
            }
        }
    }

    fun getStoryViewers(storyId: Int){

        viewModelScope.launch {
            try {
                repository.getStoryViewers(storyId)
            }catch (e: Exception){
                Log.e("StoryViewModel", e.message.toString())
            }
        }
    }

    fun deleteStory(storyId: Int){
        viewModelScope.launch {
            try {
                repository.deleteStory(storyId)
            }catch (e: Exception){
                Log.e("StoryViewModel", e.message.toString())
            }
        }
    }
}