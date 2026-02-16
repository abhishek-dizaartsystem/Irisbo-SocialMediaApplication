package com.example.sociamediaapplication.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun AutoVideo(
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            val item = MediaItem.fromUri(url)
            setMediaItem(item)
            repeatMode = ExoPlayer.REPEAT_MODE_ONE
            playWhenReady = true
            volume = 1f
            prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                this.player = player
                useController = false     // 🔥 NO buttons
            }
        }
    )
}
