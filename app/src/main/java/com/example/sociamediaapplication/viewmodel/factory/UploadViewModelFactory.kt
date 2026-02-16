package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.PostRepository
import com.example.sociamediaapplication.viewmodel.UploadViewModel

class UploadViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UploadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
