package com.example.sociamediaapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sociamediaapplication.data.repository.PageRepository
import com.example.sociamediaapplication.viewmodel.PageViewModel

class PageViewModelFactory(
    private val repository: PageRepository
): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(PageViewModel::class.java)){
            return PageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}