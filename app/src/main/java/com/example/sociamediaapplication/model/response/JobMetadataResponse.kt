package com.example.sociamediaapplication.model.response

data class JobMetadataResponse(
    val success: Boolean,
    val data: JobMetadata
)

data class JobMetadata(
    val workplace_type: List<String>,
    val job_type: List<String>,
    val experience_level: List<String>,
    val status: List<String>
)