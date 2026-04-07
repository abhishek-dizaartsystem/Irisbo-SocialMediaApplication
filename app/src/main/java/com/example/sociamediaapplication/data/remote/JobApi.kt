package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.CreateJobRequest
import com.example.sociamediaapplication.model.request.JobApplyRequest
import com.example.sociamediaapplication.model.response.ApplicationsResponse
import com.example.sociamediaapplication.model.response.JobDetailsResponse
import com.example.sociamediaapplication.model.response.JobMetadataResponse
import com.example.sociamediaapplication.model.response.JobsResponse
import com.example.sociamediaapplication.model.response.PublicJobsResponse
import com.example.sociamediaapplication.model.response.SavedJobsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface JobApi {

    @GET("api/jobs")
    suspend fun getPublicJobs(
        @Header("Authorization") token: String
    ): PublicJobsResponse

    @GET("api/jobs/my/jobs")
    suspend fun getMyJobs(
        @Header("Authorization") token: String
    ): JobsResponse

    @GET("api/jobs/my/saved")
    suspend fun getMySavedJobs(
        @Header("Authorization") token: String
    ): SavedJobsResponse

    @GET("api/jobs/my/applications")
    suspend fun getMyApplications(
        @Header("Authorization") token: String
    ): ApplicationsResponse

    @POST("api/jobs")
    suspend fun createJob(
        @Header("Authorization") token: String,
        @Body request: CreateJobRequest
    )

    @GET("api/jobs/meta")
    suspend fun getJobMetadata(): JobMetadataResponse

    @GET("api/jobs/{id}")
    suspend fun getJobDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): JobDetailsResponse

    @POST("api/jobs/{id}/apply")
    suspend fun applyToJob(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: JobApplyRequest
    )

}