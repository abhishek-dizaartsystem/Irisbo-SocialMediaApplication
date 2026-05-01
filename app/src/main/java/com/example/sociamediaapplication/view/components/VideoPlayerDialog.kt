package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun VideoPlayerDialog(
    videoUrl: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var aspectRatio by remember { mutableStateOf(1f) }
    var isPlaying by remember { mutableStateOf(true) }
    var duration by remember { mutableStateOf(0) }
    var currentPosition by remember { mutableStateOf(0) }

    var videoViewRef by remember { mutableStateOf<android.widget.VideoView?>(null) }

    // 🔄 Update progress
    LaunchedEffect(videoViewRef) {
        while (true) {
            videoViewRef?.let {
                currentPosition = it.currentPosition
                duration = it.duration
            }
            delay(300)
        }
    }

    fun formatTime(ms: Int): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return "%d:%02d".format(min, sec)
    }

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black)
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
        ) {

            // 🎥 Video
            AndroidView(
                factory = {
                    android.widget.VideoView(it).apply {

                        videoViewRef = this
                        setVideoPath(videoUrl)

                        setOnPreparedListener { mp ->
                            val w = mp.videoWidth
                            val h = mp.videoHeight

                            if (w > 0 && h > 0) {
                                aspectRatio = w.toFloat() / h.toFloat()
                            }

                            duration = mp.duration
                            mp.isLooping = false

                            start()
                            isPlaying = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
            )

            // 🔥 TOP LEFT CONTROLS (LIKE YOUR IMAGE)
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(
                        Black.copy(alpha = 0.4f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
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
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            if (isPlaying)
                                R.drawable.pause_svgrepo_com
                            else
                                R.drawable.play_svgrepo_com
                        ),
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = "${formatTime(currentPosition)} / ${formatTime(duration)}",
                    color = White
                )
            }

            // 🔥 THIN BOTTOM PROGRESS BAR
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color.Gray.copy(alpha = 0.4f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(
                            if (duration > 0)
                                currentPosition / duration.toFloat()
                            else 0f
                        )
                        .height(3.dp)
                        .background(White)
                )
            }
        }
    }
}