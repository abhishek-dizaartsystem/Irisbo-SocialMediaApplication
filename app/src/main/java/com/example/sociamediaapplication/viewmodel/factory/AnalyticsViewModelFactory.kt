package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.AnalyticsRepository
import com.example.sociamediaapplication.viewmodel.AnalyticsViewModel
import com.example.sociamediaapplication.viewmodel.EventViewModel

class AnalyticsViewModelFactory(
    private val repository: AnalyticsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AnalyticsViewModel::class.java)) {
            return AnalyticsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}