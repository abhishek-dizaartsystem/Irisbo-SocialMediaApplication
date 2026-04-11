package com.example.sociamediaapplication.model.response

data class ApplicationMetadataResponse(
    val success: Boolean,
    val data: ApplicationMetadata
)

data class ApplicationMetadata(
    val status: List<String>
)