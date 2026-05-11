package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.VideoRepository
import com.example.sociamediaapplication.viewmodel.StoryViewModel
import com.example.sociamediaapplication.viewmodel.VideoViewModel

class VideoViewModelFactory(
    private val repository: VideoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoViewModel(repository) as T
    }
}