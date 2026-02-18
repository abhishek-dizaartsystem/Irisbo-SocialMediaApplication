package com.example.sociamediaapplication.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri

fun getVideoFrameAtXSecond(context: Context, uri: Uri, second: Long): Bitmap?{
    return try {

        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, uri)

        val frameAtXSecond: Long = second * 1000_000

        // 🔥 2 seconds = 2,000,000 microseconds
        val bitmap = retriever.getFrameAtTime(
            frameAtXSecond,
            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )

        retriever.release()
        bitmap

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getFrameFromUrl(
    context: Context,
    url: String
): Bitmap? {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(url, HashMap())
        val bitmap = retriever.getFrameAtTime(
            2_000_000,
            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )
        retriever.release()
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
