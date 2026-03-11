package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.EventRepository
import com.example.sociamediaapplication.viewmodel.EventViewModel

class EventViewModelFactory(
    private val repository: EventRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}