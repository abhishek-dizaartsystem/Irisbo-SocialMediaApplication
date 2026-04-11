package com.example.sociamediaapplication.data.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.sociamediaapplication.data.remote.RetrofitClient
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


fun correctUrl(url: String?): String{

    if(url == null){
        return "${RetrofitClient.BASE_URL}uploads/1774852869892-939140931.jpg"
    }else{
        if(url?.startsWith("http") == true){

            Log.d("correctUrl_DEBUG", "$url")
            return url
        } else{
            val correct_url = url?.split("/")

            Log.d("correctUrl_DEBUG", "${RetrofitClient.BASE_URL}uploads/${correct_url?.last()}")

            return "${RetrofitClient.BASE_URL}uploads/${correct_url?.last()}"
        }
    }
}

fun correctUrl2(url: String?): String{
    if(url == null){
        return "${RetrofitClient.BASE_URL}uploads/1774852869892-939140931.jpg"
    }else{
        if(url?.startsWith("http") == true){

            Log.d("correctUrl_DEBUG", "$url")
            return url
        } else{
            val correct_url = url?.split("\\")

            Log.d("correctUrl_DEBUG", "${RetrofitClient.BASE_URL}uploads/${correct_url?.last()}")

            return "${RetrofitClient.BASE_URL}uploads/${correct_url?.last()}"
        }
    }
}

fun openUrl(context: Context, url: String?) {
    if (!url.isNullOrEmpty()) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}

