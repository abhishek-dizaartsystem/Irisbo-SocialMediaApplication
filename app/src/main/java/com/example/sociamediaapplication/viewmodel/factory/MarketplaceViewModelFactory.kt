package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.MarketplaceRepository
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

class MarketplaceViewModelFactory(
    private val repository: MarketplaceRepository
): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(MarketplaceViewModel::class.java)){
            return MarketplaceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}