package com.example.sociamediaapplication.model

import android.net.Uri

class UploadMedia(
    val uri: Uri,
    val mediaType: MediaType
)

enum class MediaType{
    IMAGE,
    VIDEO
}