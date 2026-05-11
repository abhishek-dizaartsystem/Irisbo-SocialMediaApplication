package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.VideoRepository
import com.example.sociamediaapplication.model.response.VideoCategoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val repository: VideoRepository
): ViewModel() {

    private val _categories = MutableStateFlow<VideoCategoryResponse?>(null)
    val categories: StateFlow<VideoCategoryResponse?> = _categories

    fun fetchVideoCategories(){
        viewModelScope.launch {
            try {
                _categories.value = repository.getVideoCategories()
            }catch (e: Exception){
                Log.e("VideoVM_DEBUG", e.message.toString())
            }
        }
    }
}