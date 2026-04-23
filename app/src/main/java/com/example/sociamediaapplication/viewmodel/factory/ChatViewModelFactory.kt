package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.ChatRepository
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.EventViewModel

class ChatViewModelFactory(
    private val repository: ChatRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}