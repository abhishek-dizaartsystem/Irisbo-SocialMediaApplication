package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.JobRepository
import com.example.sociamediaapplication.model.response.ApplicationsResponse
import com.example.sociamediaapplication.model.response.JobMetadataResponse
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

    private val _jobMetadata = MutableStateFlow<JobMetadataResponse?>(null)
    val jobMetadata: StateFlow<JobMetadataResponse?> = _jobMetadata

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

    fun createJob(
        title: String,
        company_name: String,
        description: String,
        responsibilities: String,
        requirements: String,
        benefits: String,
        location: String,
        workplace_type: String,
        job_type: String,
        experience_level: String,
        min_salary: Int,
        max_salary: Int,
        salary_currency: String,
        vacancies: Int,
        application_deadline: String
    ){
        viewModelScope.launch {
            try {
                repository.createJob(
                    title,
                    company_name,
                    description,
                    responsibilities,
                    requirements,
                    benefits,
                    location,
                    workplace_type,
                    job_type,
                    experience_level,
                    min_salary,
                    max_salary,
                    salary_currency,
                    vacancies,
                    application_deadline
                )
            } catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun loadJobMetadata(){
        viewModelScope.launch {
            try {
                _jobMetadata.value = repository.getJobMetadata()
            } catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }
}