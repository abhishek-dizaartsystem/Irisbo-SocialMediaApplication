package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.viewmodel.ReelsViewModel

class ReelsViewModelFactory(
    private val repository: ReelRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReelsViewModel(repository) as T
    }
}