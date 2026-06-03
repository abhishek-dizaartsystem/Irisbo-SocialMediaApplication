package com.example.sociamediaapplication.data.local.downloader

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class VideoDownloader(
    private val context: Context
) {

    suspend fun downloadVideo(
        videoId: String,
        videoUrl: String
    ): String {

        val downloadsFolder =
            File(context.filesDir, "downloads")

        if (!downloadsFolder.exists()) {
            downloadsFolder.mkdirs()
        }

        val outputFile =
            File(downloadsFolder, "$videoId.mp4")

        val request = Request.Builder()
            .url(videoUrl)
            .build()

        val response = OkHttpClient()
            .newCall(request)
            .execute()

        if (!response.isSuccessful) {
            throw Exception("Download failed")
        }

        response.body?.byteStream()?.use { input ->

            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return outputFile.absolutePath
    }
}