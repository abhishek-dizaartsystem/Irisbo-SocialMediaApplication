package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.Reel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ReelRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.reelApi

    // ✅ fetch reels
    suspend fun getReels(): List<Reel> {
        return api.getAllReels().reels
    }

    // ✅ upload reel (single video)
    suspend fun uploadReel(
        captionText: String?,
        uri: Uri?,
        context: Context
    ) {

        val token = tokenManager.getToken()
            ?: throw IllegalStateException("No token")

        if (uri == null) throw IllegalStateException("Video missing")

        val file = uriToFile(uri, context)

        val mime = context.contentResolver.getType(uri) ?: "video/*"

        val requestFile = file.asRequestBody(
            mime.toMediaTypeOrNull()
        )

        val videoPart = MultipartBody.Part.createFormData(
            name = "video",      // 🔥 must match backend
            filename = file.name,
            body = requestFile
        )

        val caption =
            captionText?.toRequestBody("text/plain".toMediaType())

        api.uploadReel(
            token = "Bearer $token",
            video = videoPart,
            caption = caption
        )
    }
}
