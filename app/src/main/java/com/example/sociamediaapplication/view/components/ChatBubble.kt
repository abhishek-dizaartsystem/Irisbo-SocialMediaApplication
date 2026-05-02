package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.formatToTime
import com.example.sociamediaapplication.model.ChatMessage
import com.example.sociamediaapplication.model.response.Attachment
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DGrey

@Composable
fun ChatBubble(
    message: ChatMessage,
    isRead: Boolean = false,
    onDeleteClick: () -> Unit = {},
    attachments: List<Attachment> = emptyList()
) {
    var showMenu by remember { mutableStateOf(false) }
    var viewerStartIndex by remember { mutableStateOf(0) }
    var showViewer by remember { mutableStateOf(false) }
    var showVideoPlayer by remember { mutableStateOf(false) }

    val safeAttachments = attachments.orEmpty()

    val imageAttachments = safeAttachments.filter { it.file_type == "image" }
    val videoAttachments = safeAttachments.filter { it.file_type == "video" }
    val audioAttachments = safeAttachments.filter { it.file_type == "audio" }

    // Audio player state — hoisted here so it's accessible everywhere
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<android.media.MediaPlayer?>(null) }
    var progress by remember { mutableStateOf(0f) }

    if (showViewer) {
        ImageViewerDialog(
            attachments = imageAttachments,
            startIndex = viewerStartIndex,
            onDismiss = { showViewer = false }
        )
    }

    if (showVideoPlayer && videoAttachments.isNotEmpty()) {
        VideoPlayerDialog(
            videoUrl = correctUrl(videoAttachments[0].file_url),
            onDismiss = { showVideoPlayer = false }
        )
    }

    // Release player on dispose
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    // Progress update loop
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            mediaPlayer?.let {
                if (it.duration > 0) {
                    progress = it.currentPosition / it.duration.toFloat()
                }
            }
            kotlinx.coroutines.delay(200)
        }
    }

    val bubbleColor = if (message.isUser) Color(0xFFE0E0E0) else Color(0xFFB3E5FC)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 60.dp, max = 280.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = { showMenu = true })
                }
        ) {
            Column(
                horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
            ) {

                // ── IMAGE ──────────────────────────────────────────────
                if (imageAttachments.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(bubbleColor)
                            .clickable {
                                viewerStartIndex = 0
                                showViewer = true
                            }
                    ) {
                        AsyncImage(
                            model = correctUrl(imageAttachments[0].file_url),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(14.dp)),
                            contentScale = ContentScale.Crop
                        )

                        if (imageAttachments.size > 1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.45f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+${imageAttachments.size - 1}",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.35f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formatToTime(message.msgTime),
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            if (message.isUser) {
                                Icon(
                                    painter = painterResource(R.drawable.double_check_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = if (isRead) Blue else DGrey
                                )
                            }
                        }
                    }
                }

                // ── VIDEO ──────────────────────────────────────────────
                if (videoAttachments.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Black)
                            .clickable { showVideoPlayer = true }
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(correctUrl(videoAttachments[0].file_url))
                                .videoFrameMillis(1000)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Icon(
                            painter = painterResource(R.drawable.video_svgrepo_com),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(40.dp)
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.4f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formatToTime(message.msgTime),
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            if (message.isUser) {
                                Icon(
                                    painter = painterResource(R.drawable.double_check_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = if (isRead) Blue else DGrey
                                )
                            }
                        }
                    }
                }

                // ── AUDIO ──────────────────────────────────────────────
                if (audioAttachments.isNotEmpty()) {
                    val audio = audioAttachments[0]

                    var totalDuration by remember { mutableStateOf(0) }
                    var remainingSeconds by remember { mutableStateOf(0) }
                    var isSeeking by remember { mutableStateOf(false) }
                    var seekPosition by remember { mutableStateOf(0f) }

                    // Fetch duration on first load without playing
                    LaunchedEffect(audio.file_url) {
                        if (totalDuration == 0) {
                            try {
                                val probe = android.media.MediaPlayer().apply {
                                    setDataSource(correctUrl(audio.file_url))
                                    prepare()
                                }
                                totalDuration = probe.duration / 1000
                                remainingSeconds = totalDuration
                                probe.release()
                            } catch (e: Exception) {
                                // Ignore — duration will show when user plays
                            }
                        }
                    }

                    fun formatAudioTime(seconds: Int): String {
                        val m = seconds / 60
                        val s = seconds % 60
                        return String.format("%02d:%02d", m, s)
                    }

                    // Progress + countdown loop
                    LaunchedEffect(isPlaying) {
                        if (isPlaying) {
                            while (isPlaying) {
                                if (!isSeeking) {
                                    mediaPlayer?.let {
                                        if (it.duration > 0) {
                                            val elapsed = it.currentPosition / 1000
                                            remainingSeconds = (totalDuration - elapsed).coerceAtLeast(0)
                                            progress = it.currentPosition / it.duration.toFloat()
                                        }
                                    }
                                }
                                kotlinx.coroutines.delay(200)
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .width(260.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(bubbleColor)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Play / Pause
                                Icon(
                                    painter = painterResource(
                                        if (isPlaying) R.drawable.pause_svgrepo_com
                                        else R.drawable.play_svgrepo_com
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickable {
                                            if (isPlaying) {
                                                mediaPlayer?.pause()
                                                isPlaying = false
                                            } else {
                                                if (mediaPlayer == null) {
                                                    mediaPlayer = android.media.MediaPlayer().apply {
                                                        setDataSource(correctUrl(audio.file_url))
                                                        prepare()
                                                        totalDuration = (duration / 1000)
                                                        remainingSeconds = totalDuration
                                                        setOnCompletionListener {
                                                            isPlaying = false
                                                            progress = 0f
                                                            remainingSeconds = totalDuration
                                                        }
                                                    }
                                                } else if (totalDuration == 0) {
                                                    totalDuration = ((mediaPlayer?.duration ?: 0) / 1000)
                                                    remainingSeconds = totalDuration
                                                }
                                                mediaPlayer?.start()
                                                isPlaying = true
                                            }
                                        }
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                // Seek slider — takes remaining space
                                androidx.compose.material3.Slider(
                                    value = if (isSeeking) seekPosition else progress,
                                    onValueChange = { value ->
                                        isSeeking = true
                                        seekPosition = value
                                        if (totalDuration > 0) {
                                            remainingSeconds = (totalDuration - (value * totalDuration).toInt())
                                                .coerceAtLeast(0)
                                        }
                                    },
                                    onValueChangeFinished = {
                                        mediaPlayer?.let { mp ->
                                            if (mp.duration > 0) {
                                                val seekTo = (seekPosition * mp.duration).toInt()
                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                                    mp.seekTo(seekTo.toLong(), android.media.MediaPlayer.SEEK_CLOSEST)
                                                } else {
                                                    mp.seekTo(seekTo)
                                                }
                                                progress = seekPosition
                                                remainingSeconds = (totalDuration - seekTo / 1000).coerceAtLeast(0)
                                            }
                                        }
                                        isSeeking = false
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(24.dp),
                                    colors = androidx.compose.material3.SliderDefaults.colors(
                                        thumbColor = if (message.isUser) Color.DarkGray else Color(0xFF0277BD),
                                        activeTrackColor = if (message.isUser) Color.DarkGray else Color(0xFF0277BD),
                                        inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
                                    )
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                // Duration
                                Text(
                                    text = if (isPlaying || progress > 0f)
                                        formatAudioTime(remainingSeconds)
                                    else
                                        formatAudioTime(totalDuration),
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }

                            // Message send time + read tick — bottom right
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = formatToTime(message.msgTime),
                                    fontSize = 9.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(end = 2.dp)
                                )
                                if (message.isUser) {
                                    Icon(
                                        painter = painterResource(R.drawable.double_check_svgrepo_com),
                                        contentDescription = null,
                                        modifier = Modifier.size(12.dp),
                                        tint = if (isRead) Blue else DGrey
                                    )
                                }
                            }
                        }
                    }
                }

                // ── TEXT ───────────────────────────────────────────────
                if (!message.message.isNullOrBlank()) {
                    Box(
                        modifier = Modifier
                            .background(color = bubbleColor, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                            .widthIn(min = 60.dp, max = 260.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = message.message,
                                color = Black,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = formatToTime(message.msgTime),
                                    fontSize = 10.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(end = 2.dp)
                                )
                                if (message.isUser) {
                                    Icon(
                                        painter = painterResource(R.drawable.double_check_svgrepo_com),
                                        contentDescription = null,
                                        modifier = Modifier.size(12.dp),
                                        tint = if (isRead) Blue else DGrey
                                    )
                                }
                            }
                        }
                    }
                }
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = { showMenu = false; onDeleteClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubblePreview() {
    ChatBubble(
        ChatMessage("Hello Kartik", true, "12:44"),
    )
}