package com.example.sociamediaapplication.data.utils

import android.content.Context
import android.net.Uri
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


fun getMimeType(context: Context, uri: Uri): String {
    return context.contentResolver.getType(uri) ?: "image/*"
}

