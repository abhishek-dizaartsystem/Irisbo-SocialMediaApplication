package com.example.sociamediaapplication.data.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun uriToFile(uri: Uri, context: Context): File {

    val contentResolver = context.contentResolver
    val mime = contentResolver.getType(uri) ?: "image/jpeg"

    val extension = when {
        mime.contains("jpeg") -> ".jpg"
        mime.contains("png") -> ".png"
        mime.contains("mp4") -> ".mp4"
        mime.contains("video") -> ".mp4"
        else -> ".jpg"
    }

    val file = File(
        context.cacheDir,
        "upload_${System.currentTimeMillis()}$extension"
    )

    contentResolver.openInputStream(uri)!!.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return file
}

fun fileToMultipart(name: String, file: File, mime: String): MultipartBody.Part {
    val requestBody = file.asRequestBody(mime.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, file.name, requestBody)
}

fun String.toPart(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())


fun getMimeType(context: Context, uri: Uri): String {
    return context.contentResolver.getType(uri) ?: "image/*"
}

