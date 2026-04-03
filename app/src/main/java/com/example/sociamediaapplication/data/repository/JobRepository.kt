package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.ApplicationsResponse
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

}