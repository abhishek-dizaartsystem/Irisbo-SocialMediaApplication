package com.example.sociamediaapplication.view.components

import android.media.MediaPlayer
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun VideoPlayerDialog(
    videoUrl: String,
    onDismiss: () -> Unit
) {
    var aspectRatio by remember { mutableStateOf(16f / 9f) }
    var isPlaying by remember { mutableStateOf(true) }
    var duration by remember { mutableStateOf(0) }
    var currentPosition by remember { mutableStateOf(0) }
    var isSeeking by remember { mutableStateOf(false) }
    var seekPosition by remember { mutableStateOf(0f) }

    var videoViewRef by remember { mutableStateOf<android.widget.VideoView?>(null) }
    // Store MediaPlayer ref for precise seeking
    var mediaPlayerRef by remember { mutableStateOf<MediaPlayer?>(null) }

    LaunchedEffect(videoViewRef) {
        while (true) {
            if (!isSeeking) {
                videoViewRef?.let {
                    currentPosition = it.currentPosition
                    if (it.duration > 0) duration = it.duration
                }
            }
            delay(200)
        }
    }

    fun formatTime(ms: Int): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return "%d:%02d".format(min, sec)
    }

    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black)
        ) {
            AndroidView(
                factory = { ctx ->
                    android.widget.VideoView(ctx).apply {
                        videoViewRef = this
                        setVideoPath(videoUrl)

                        setOnPreparedListener { mp ->
                            mediaPlayerRef = mp // 👈 store MediaPlayer reference

                            val w = mp.videoWidth
                            val h = mp.videoHeight
                            if (w > 0 && h > 0) {
                                aspectRatio = w.toFloat() / h.toFloat()
                            }
                            duration = mp.duration
                            mp.isLooping = false
                            mp.start()
                            isPlaying = true
                        }

                        setOnCompletionListener {
                            isPlaying = false
                            currentPosition = 0
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        videoViewRef?.let {
                            if (it.isPlaying) {
                                it.pause()
                                isPlaying = false
                            } else {
                                it.start()
                                isPlaying = true
                            }
                        }
                    }
            )

            // Close button
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Black.copy(alpha = 0.5f), RoundedCornerShape(50))
                    .size(36.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_svgrepo_com),
                    contentDescription = "Close",
                    tint = White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Bottom controls
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Black.copy(alpha = 0.5f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Slider(
                    value = if (isSeeking) seekPosition
                    else if (duration > 0) currentPosition / duration.toFloat()
                    else 0f,
                    onValueChange = { value ->
                        isSeeking = true
                        seekPosition = value
                    },
                    onValueChangeFinished = {
                        if (duration > 0) {
                            val seekTo = (seekPosition * duration).toInt()

                            // Use MediaPlayer directly for SEEK_CLOSEST precision
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mediaPlayerRef != null) {
                                mediaPlayerRef?.seekTo(seekTo.toLong(), MediaPlayer.SEEK_CLOSEST)
                            } else {
                                videoViewRef?.seekTo(seekTo)
                            }

                            currentPosition = seekTo
                        }
                        isSeeking = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = White,
                        activeTrackColor = White,
                        inactiveTrackColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            videoViewRef?.let {
                                if (it.isPlaying) {
                                    it.pause()
                                    isPlaying = false
                                } else {
                                    it.start()
                                    isPlaying = true
                                }
                            }
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isPlaying) R.drawable.pause_svgrepo_com
                                else R.drawable.play_svgrepo_com
                            ),
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Text(
                        text = "${formatTime(currentPosition)} / ${formatTime(duration)}",
                        color = White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}