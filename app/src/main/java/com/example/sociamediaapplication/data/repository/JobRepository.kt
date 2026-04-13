package com.example.sociamediaapplication.data.repository

import androidx.compose.runtime.referentialEqualityPolicy
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.request.CreateJobRequest
import com.example.sociamediaapplication.model.request.JobApplyRequest
import com.example.sociamediaapplication.model.request.UpdateApplicationStatusRequest
import com.example.sociamediaapplication.model.response.ApplicantDetailsResponse
import com.example.sociamediaapplication.model.response.ApplicantsResponse
import com.example.sociamediaapplication.model.response.ApplicationMetadataResponse
import com.example.sociamediaapplication.model.response.ApplicationsResponse
import com.example.sociamediaapplication.model.response.BasicResponse2
import com.example.sociamediaapplication.model.response.JobDetailsResponse
import com.example.sociamediaapplication.model.response.JobMetadataResponse
import com.example.sociamediaapplication.model.response.JobsResponse
import com.example.sociamediaapplication.model.response.PublicJobsResponse
import com.example.sociamediaapplication.model.response.SavedJobsResponse

class JobRepository(
    val tokenManager: TokenManager
) {

    val api = RetrofitClient.jobApi

    suspend fun getPublicJobs(): PublicJobsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getPublicJobs(token)
    }

    suspend fun getMyJobs(): JobsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMyJobs(token)
    }

    suspend fun getSavedJobs(): SavedJobsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMySavedJobs(token)
    }

    suspend fun getMyApplications(): ApplicationsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMyApplications(token)
    }

    suspend fun createJob(
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
        val token = "Bearer ${tokenManager.getToken()}"

        api.createJob(
            token,
            CreateJobRequest(
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
        )
    }

    suspend fun getJobMetadata(): JobMetadataResponse{
        return api.getJobMetadata()
    }

    suspend fun getJobDetails(
        id: Int
    ): JobDetailsResponse{

        val token = "Bearer ${tokenManager.getToken()}"

        return api.getJobDetails(token, id)
    }

    suspend fun applyToJob(
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
        val token = "Bearer ${tokenManager.getToken()}"

        return api.applyToJob(
            token,
            id,
            JobApplyRequest(
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
        )
    }

    suspend fun saveJob(id: Int): BasicResponse2{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.saveJob(token, id)
    }

    suspend fun unsaveJob(id: Int): BasicResponse2{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.unsaveJob(token, id)
    }

    suspend fun getApplicants(id: Int): ApplicantsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getApplicants(token, id)
    }

    suspend fun getApplicationMetadata(): ApplicationMetadataResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getApplicationMetadata(token)
    }

    suspend fun getApplicantDetails(
        jobId: Int,
        applicantId: Int
    ): ApplicantDetailsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getApplicantDetails(token, jobId, applicantId)
    }

    suspend fun updateApplicantStatus(
        jobId: Int,
        applicationId: Int,
        status: String
    ){
        val token = "Bearer ${tokenManager.getToken()}"

        api.updateApplicationStatus(token, jobId, applicationId, UpdateApplicationStatusRequest(status))
    }

    suspend fun deleteJob(
        jobId: Int
    ){
        val token = "Bearer ${tokenManager.getToken()}"

        api.deleteJob(token, jobId)
    }

    suspend fun withdrawApplication(
        jobId: Int
    ){
        val token = "Bearer ${tokenManager.getToken()}"

        api.withdrawApplication(token, jobId)
    }
}