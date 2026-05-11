package com.example.sociamediaapplication.view.components

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

@Composable
fun VideoUploadThumbnail(videoUri: Uri) {

    val context = LocalContext.current

    val bitmap by remember(videoUri) {
        mutableStateOf(
            try {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(context, videoUri)
                retriever.getFrameAtTime(0)
            } catch (e: Exception) {
                null
            }
        )
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )
    }
}