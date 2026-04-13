package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.JobRepository
import com.example.sociamediaapplication.model.response.ApplicantDetailsResponse
import com.example.sociamediaapplication.model.response.ApplicantsResponse
import com.example.sociamediaapplication.model.response.ApplicationMetadataResponse
import com.example.sociamediaapplication.model.response.ApplicationsResponse
import com.example.sociamediaapplication.model.response.JobDetailsResponse
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

    private val _applicationMetadata = MutableStateFlow<ApplicationMetadataResponse?>(null)
    val applicationMetadata: StateFlow<ApplicationMetadataResponse?> = _applicationMetadata

    private val _jobDetails = MutableStateFlow<JobDetailsResponse?>(null)
    val jobDetails: StateFlow<JobDetailsResponse?> = _jobDetails

    private val _jobApplicants = MutableStateFlow<ApplicantsResponse?>(null)
    val jobApplicants: StateFlow<ApplicantsResponse?> = _jobApplicants

    private val _applicant = MutableStateFlow<ApplicantDetailsResponse?>(null)
    val applicant: StateFlow<ApplicantDetailsResponse?> = _applicant

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

    fun loadJobDetails(id: Int){
        viewModelScope.launch {
            try {
                _jobDetails.value = repository.getJobDetails(id)
            }catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }

    }

    fun applyToJob(
        id:Int,
        full_name: String,
        email: String,
        phone: String,
        resume_url: String,
        cover_letter: String,
        experience_years: Float,
        current_company: String,
        current_ctc: Int,
        expected_ctc: Int,
        notice_period: String,
        portfolio_url: String,
        linkedin_url: String
    ){
        viewModelScope.launch {
            try {
                repository.applyToJob(
                    id,
                    full_name,
                    email,
                    phone,
                    resume_url,
                    cover_letter,
                    experience_years,
                    current_company,
                    current_ctc,
                    expected_ctc,
                    notice_period,
                    portfolio_url,
                    linkedin_url
                )
            }catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun saveJobToggle(jobId: Int, isSaved: Boolean){
        viewModelScope.launch {
            try {
                if (isSaved) {
                    repository.unsaveJob(jobId)
                } else {
                    repository.saveJob(jobId)
                }

                // Refresh saved jobs list after toggle

                loadPublicJobs()

            } catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun loadJobApplicants(jobId: Int){
        viewModelScope.launch {
            try {
                Log.d("JobViewModel", "jobId = $jobId")
                _jobApplicants.value = repository.getApplicants(jobId)
            } catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun loadApplicationMetadata(){
        viewModelScope.launch {
            try {
                _applicationMetadata.value = repository.getApplicationMetadata()
            }catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun loadApplicantDetails(
        jobId: Int,
        applicantId: Int
    ){
        viewModelScope.launch {
            try {
                _applicant.value = repository.getApplicantDetails(jobId, applicantId)
            } catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun updateApplicationStatus(
        jobId: Int,
        applicationId: Int,
        applicantId: Int,
        status: String
    ){
        viewModelScope.launch {
            try {
                repository.updateApplicantStatus(jobId, applicationId, status)
                loadApplicantDetails(jobId, applicantId)
            } catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun deleteJob(jobId: Int){
        viewModelScope.launch {
            try {
                repository.deleteJob(jobId)
                Log.d("JobViewModel", "Job Deleted successfully")
                loadMyJobs()
            } catch (e: Exception){
                Log.e("JobViewModel", e.message, e)
            }
        }
    }

    fun withdrawApplication(jobId: Int){
        viewModelScope.launch {
            try {
                Log.d("JobViewModel", jobId.toString())
                repository.withdrawApplication(jobId)
            }catch (e: Exception){
                Log.e("JobViewModel", e.message.toString())
            }
        }
    }
}