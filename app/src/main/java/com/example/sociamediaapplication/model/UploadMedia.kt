package com.example.sociamediaapplication.model

import android.net.Uri

data class UploadMedia(
    val uri: Uri,
    val mediaType: MediaType
)

enum class MediaType{
    IMAGE,
    VIDEO
}

enum class UploadType {
    POST,
    REEL
}