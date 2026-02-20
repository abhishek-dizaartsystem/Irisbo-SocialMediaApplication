package com.example.sociamediaapplication.model.request

import android.net.Uri

data class EditProfileRequest(
    val name: String?,
    val username: String?,
    val bio: String?,
    val work: String?,
    val education: String?,
    val profileUri: Uri?,
    val coverUri: Uri?,
    val phone: String?
)
