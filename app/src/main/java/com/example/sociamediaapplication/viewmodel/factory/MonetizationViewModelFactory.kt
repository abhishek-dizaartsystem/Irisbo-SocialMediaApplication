package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.AnalyticsRepository
import com.example.sociamediaapplication.data.repository.MonetizationRepository
import com.example.sociamediaapplication.viewmodel.AnalyticsViewModel
import com.example.sociamediaapplication.viewmodel.MonetizationViewModel

class MonetizationViewModelFactory(
    private val repository: MonetizationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MonetizationViewModel::class.java)){
            return MonetizationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}