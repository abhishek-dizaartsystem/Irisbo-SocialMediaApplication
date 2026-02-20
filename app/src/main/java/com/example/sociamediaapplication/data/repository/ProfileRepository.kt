package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.request.EditProfileRequest
import com.example.sociamediaapplication.model.response.ProfileResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProfileRepository(
    private val tokenManager: TokenManager
) {
    private val api = RetrofitClient.profileApi

    suspend fun getProfile(): ProfileResponse {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token found")

        return api.getProfile("Bearer $token")
    }

    suspend fun editProfile(
        request: EditProfileRequest,
        context: Context
    ) {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        fun textPart(value: String?) =
            value?.toRequestBody("text/plain".toMediaType())

        val profilePart = request.profileUri?.let { uri ->
            val file = uriToFile(uri, context)
            val mime = context.contentResolver.getType(uri) ?: "image/*"

            MultipartBody.Part.createFormData(
                "profile_img",
                file.name,
                file.asRequestBody(mime.toMediaTypeOrNull())
            )
        }

        val coverPart = request.coverUri?.let { uri ->
            val file = uriToFile(uri, context)
            val mime = context.contentResolver.getType(uri) ?: "image/*"

            MultipartBody.Part.createFormData(
                "cover_img",
                file.name,
                file.asRequestBody(mime.toMediaTypeOrNull())
            )
        }

        api.editProfile(
            token = "Bearer $token",
            name = textPart(request.name),
            username = textPart(request.username),
            bio = textPart(request.bio),
            work = textPart(request.work),
            education = textPart(request.education),
            profile_img = profilePart,
            cover_img = coverPart
        )
    }

    suspend fun uploadProfileImage(uri: Uri, context: Context) {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        val file = uriToFile( uri,context)

        val mimeType = context.contentResolver.getType(uri) ?: "image/*"

        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())

        val body = MultipartBody.Part.createFormData(
            "profile_img",              // must match multer field name
            file.name.ifEmpty { "profileImg.jpg" },
            requestFile
        )

        api.uploadProfileImage("Bearer $token", body)
    }


    suspend fun uploadCoverImage(uri: Uri, context: Context) {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        val file = uriToFile(uri, context)

        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"

        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())

        val body = MultipartBody.Part.createFormData(
            "cover_img",              // must match multer field name
            file.name.ifEmpty { "coverImg.jpg" },
            requestFile
        )

        api.uploadCoverImage("Bearer $token", body)
    }

}