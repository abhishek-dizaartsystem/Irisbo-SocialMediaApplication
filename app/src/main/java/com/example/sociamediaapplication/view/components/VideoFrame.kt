package com.example.sociamediaapplication.view.components

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.Grey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun VideoFrame(
    videoUrl: String,
    isFullscreen: Boolean,           // 👈 receive from outside
    onFullscreenToggle: () -> Unit   // 👈 callback to toggle
) {
    val context = LocalContext.current
    val activity = context as? android.app.Activity

    val player = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = false
        }
    }

    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var showControls by remember { mutableStateOf(true) }
    var isMuted by remember { mutableStateOf(false) }
    var controlsJob by remember { mutableStateOf<kotlinx.coroutines.Job?>(null) }

    val scope = androidx.compose.runtime.rememberCoroutineScope()

    fun resetControlsTimer() {
        controlsJob?.cancel()
        showControls = true
        controlsJob = scope.launch {
            delay(3000)
            showControls = false
        }
    }

    LaunchedEffect(player) {
        while (true) {
            currentPosition = player.currentPosition
            duration = if (player.duration > 0) player.duration else 0L
            sliderPosition = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f
            isPlaying = player.isPlaying
            delay(500)
        }
    }

    // Auto-hide controls on start
    LaunchedEffect(Unit) {
        delay(3000)
        showControls = false
    }

    DisposableEffect(player) {
        onDispose { player.release() }
    }

    // Fullscreen orientation handling
//    LaunchedEffect(isFullscreen) {
//        if (isFullscreen) {
//            activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//            activity?.window?.decorView?.systemUiVisibility = (
//                    android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
//                            android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                            android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    )
//        } else {
//            activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            activity?.window?.decorView?.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_VISIBLE
//        }
//    }

    // Replace this:
// LaunchedEffect(isFullscreen) { ... }

// With this:
    DisposableEffect(isFullscreen) {
        if (isFullscreen) {
            activity?.requestedOrientation =
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            @Suppress("DEPRECATION")
            activity?.window?.decorView?.systemUiVisibility = (
                    android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
                            android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        } else {
            activity?.requestedOrientation =
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            @Suppress("DEPRECATION")
            activity?.window?.decorView?.systemUiVisibility =
                android.view.View.SYSTEM_UI_FLAG_VISIBLE
        }

        onDispose {
            // Restore portrait when composable leaves (user navigates away)
            activity?.requestedOrientation =
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            @Suppress("DEPRECATION")
            activity?.window?.decorView?.systemUiVisibility =
                android.view.View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    val aspectRatio = if (isFullscreen) {
        // In fullscreen use screen dimensions
        val dm = context.resources.displayMetrics
        dm.widthPixels.toFloat() / dm.heightPixels.toFloat()
    } else 1.8f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black)
            .clickable {
                showControls = !showControls
                if (showControls) resetControlsTimer()
            }
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    useController = false
                    this.player = player
                    resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
                }
            },
            update = { playerView ->
                if (playerView.player != player) playerView.player = player
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
        )

        // Thin progress bar at very bottom (always visible, like YouTube)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(2.dp)
                .background(Color.Gray.copy(alpha = 0.4f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(sliderPosition)
                    .height(2.dp)
                    .background(Color.Red)
            )
        }

        // Controls overlay — shown/hidden
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .background(Black.copy(alpha = 0.4f))
            ) {
                // Center play/pause + skip buttons
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rewind 10s
                    IconButton(
                        onClick = {
                            player.seekTo((player.currentPosition - 10000).coerceAtLeast(0))
                            resetControlsTimer()
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.skip_forward_svgrepo_com),
                            contentDescription = "Rewind",
                            modifier = Modifier
                                .size(32.dp)
                                .rotate(180f),
                            tint = Color.White
                        )
                    }

                    // Play / Pause (large center button)
                    IconButton(
                        onClick = {
                            if (player.isPlaying) player.pause() else player.play()
                            isPlaying = player.isPlaying
                            resetControlsTimer()
                        },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isPlaying) R.drawable.pause_svgrepo_com
                                else R.drawable.play_svgrepo_com
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.White
                        )
                    }

                    // Forward 10s
                    IconButton(
                        onClick = {
                            player.seekTo(player.currentPosition + 10000)
                            resetControlsTimer()
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.skip_forward_svgrepo_com),
                            contentDescription = "Forward",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
                }

                // Bottom controls bar
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    // Seekable slider
                    Slider(
                        value = sliderPosition,
                        onValueChange = {
                            sliderPosition = it
                            resetControlsTimer()
                        },
                        onValueChangeFinished = {
                            player.seekTo((sliderPosition * duration).toLong())
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp),
                        colors = androidx.compose.material3.SliderDefaults.colors(
                            thumbColor = Color.Red,
                            activeTrackColor = Color.Red,
                            inactiveTrackColor = Color.White.copy(alpha = 0.4f)
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                    ) {
                        // Time
                        Text(
                            text = "${formatDuration(currentPosition)} / ${formatDuration(duration)}",
                            color = Color.White,
                            fontSize = androidx.compose.ui.unit.TextUnit(12f, androidx.compose.ui.unit.TextUnitType.Sp),
                            modifier = Modifier.padding(start = 4.dp)
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Mute toggle
                            IconButton(
                                onClick = {
                                    isMuted = !isMuted
                                    player.volume = if (isMuted) 0f else 1f
                                    resetControlsTimer()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        if (isMuted) R.drawable.closed_caption_svgrepo_com
                                        else R.drawable.settings_logo
                                    ),
                                    contentDescription = "Mute",
                                    modifier = Modifier.size(18.dp),
                                    tint = Color.White
                                )
                            }

                            // Fullscreen toggle
                            IconButton(
                                onClick = {
                                    onFullscreenToggle()
                                    resetControlsTimer()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        if (isFullscreen) R.drawable.full_screen_exit_svgrepo_com
                                        else R.drawable.full_screen_svgrepo_com
                                    ),
                                    contentDescription = "Fullscreen",
                                    modifier = Modifier.size(18.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatDuration(ms: Long): String {

    val totalSeconds = ms / 1000

    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return if (hours > 0) {

        String.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )

    } else {

        String.format(
            "%02d:%02d",
            minutes,
            seconds
        )
    }
}