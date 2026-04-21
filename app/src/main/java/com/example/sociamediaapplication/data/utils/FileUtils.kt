package com.example.sociamediaapplication.data.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.sociamediaapplication.data.remote.RetrofitClient
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

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

            Log.d("correctUrl2_DEBUG", "$url")
            return url
        } else{
            val correct_url = url?.split("\\")

            Log.d("correctUrl2_DEBUG", "${RetrofitClient.BASE_URL}uploads/${correct_url?.last()}")

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

suspend fun getVideoDuration(context: Context, url: String): Long {
    return suspendCancellableCoroutine { cont ->

        val player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(url)

        player.setMediaItem(mediaItem)

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    val duration = player.duration
                    player.release()

                    if (!cont.isCompleted) {
                        cont.resume(duration) {}
                    }
                }
            }
        }

        player.addListener(listener)
        player.prepare()

        cont.invokeOnCancellation {
            player.release()
        }
    }
}

fun captureViewBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(
        view.width,
        view.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun captureCanvasBitmap(view: View, rect: Rect): Bitmap {
    val fullBitmap = Bitmap.createBitmap(
        view.width,
        view.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(fullBitmap)
    view.draw(canvas)

    return Bitmap.createBitmap(
        fullBitmap,
        rect.left,
        rect.top,
        rect.width(),
        rect.height()
    )
}

fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "story_${System.currentTimeMillis()}.png")

    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    return file.toUri()
}