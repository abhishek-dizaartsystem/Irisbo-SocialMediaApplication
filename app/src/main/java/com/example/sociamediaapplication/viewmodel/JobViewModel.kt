package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.JobRepository
import com.example.sociamediaapplication.model.response.ApplicationsResponse
import com.example.sociamediaapplication.model.response.JobsResponse
import com.example.sociamediaapplication.model.response.PublicJobsResponse
import com.example.sociamediaapplication.model.response.SavedJobsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JobViewModel(
    private val repository: JobRepository
): ViewModel() {

    private val _publicJobs = MutableStateFlow<PublicJobsResponse?>(null)
    val publicJobs: StateFlow<PublicJobsResponse?> = _publicJobs

    private val _myJobs = MutableStateFlow<JobsResponse?>(null)
    val myJobs: StateFlow<JobsResponse?> = _myJobs

    private val _savedJobs = MutableStateFlow<SavedJobsResponse?>(null)
    val savedJobs: StateFlow<SavedJobsResponse?> = _savedJobs

    private val _myApplications = MutableStateFlow<ApplicationsResponse?>(null)
    val myApplications: StateFlow<ApplicationsResponse?> = _myApplications

    fun loadPublicJobs(){
        viewModelScope.launch {
            try {
                _publicJobs.value = repository.getPublicJobs()
            }catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun loadMyJobs(){
        viewModelScope.launch {
            try {
                _myJobs.value = repository.getMyJobs()
            }catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun loadSavedJobs(){
        viewModelScope.launch {
            try {
                _savedJobs.value =repository.getSavedJobs()
            }catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun loadMyApplications(){
        viewModelScope.launch {
            try {
                _myApplications.value = repository.getMyApplications()
            }catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }
}