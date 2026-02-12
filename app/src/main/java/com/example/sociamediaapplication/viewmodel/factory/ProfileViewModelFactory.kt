package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.ProfileRepository
import com.example.sociamediaapplication.viewmodel.ProfileViewModel

class ProfileViewModelFactory(
    private val repository: ProfileRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return ProfileViewModel(repository) as T
    }
}
