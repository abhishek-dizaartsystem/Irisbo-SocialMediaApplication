package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.viewinterop.AndroidView
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

    val imageAttachments = attachments.filter { it.file_type == "image" }
    val videoAttachments = attachments.filter { it.file_type == "video" }

    if (showViewer) {
        ImageViewerDialog(
            attachments = imageAttachments,
            startIndex = viewerStartIndex,
            onDismiss = { showViewer = false }
        )
    }

    if (showVideoPlayer && videoAttachments.isNotEmpty()) {

        val video = videoAttachments[0]

        VideoPlayerDialog(
            videoUrl = correctUrl(video.file_url),
            onDismiss = { showVideoPlayer = false }
        )
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
                // Image attachment inside bubble shape
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

                        // Overlay count if more than 1
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

                        // Time + tick inside image bubble bottom-right
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

                if (videoAttachments.isNotEmpty()) {

                    val video = videoAttachments[0]

                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Black)
                            .clickable{
                                showVideoPlayer = true
                            }
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(correctUrl(video.file_url))
                                .videoFrameMillis(1000) // 👈 thumbnail at 1 second
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // ▶️ Play icon overlay
                        Icon(
                            painter = painterResource(R.drawable.video_svgrepo_com),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(40.dp)
                        )

                        // ⏱ Time + ticks (reuse your existing logic)
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
                                color = Color.White
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

                // Text bubble — shown below image if both exist, or alone
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
                            // Time + tick inline with text
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

                // Time row only when there's NO text (image-only message)
                // Already handled inside the image Box above
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
fun ChatBubblePreview(){
    ChatBubble(
        ChatMessage("Hello Kartik", true, "12:44"),
    )
}