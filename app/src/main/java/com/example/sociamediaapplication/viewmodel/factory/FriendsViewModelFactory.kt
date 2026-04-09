package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.FriendRepository
import com.example.sociamediaapplication.viewmodel.EventViewModel
import com.example.sociamediaapplication.viewmodel.FriendViewModel

class FriendsViewModelFactory(
    private val repository: FriendRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            return FriendViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}