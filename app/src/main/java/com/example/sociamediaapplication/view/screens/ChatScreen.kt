package com.example.sociamediaapplication.view.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.AudioRecorder
import com.example.sociamediaapplication.data.utils.compressImage
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.formatToDate2
import com.example.sociamediaapplication.data.utils.formatToTime
import com.example.sociamediaapplication.data.utils.formatToTime2
import com.example.sociamediaapplication.model.ChatMessage
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.ChatBubble
import com.example.sociamediaapplication.view.components.DateSeparator
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    conversationId: Int? = 0
) {
    var typeMessage by remember { mutableStateOf("") }
    var showAttachmentMenu by remember { mutableStateOf(false) }
    var recordingState by remember { mutableStateOf(RecordingState.IDLE) }
    var dragOffset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }
    var recordingDuration by remember { mutableStateOf(0) }

    var photoUriString by rememberSaveable { mutableStateOf<String?>(null) }
    val photoUri = photoUriString?.let { Uri.parse(it) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var photoFile by remember { mutableStateOf<File?>(null) }
    val replyMessage by chatViewModel.replyToMessage.collectAsState()
    val typingUsers by chatViewModel.typingUsers.collectAsState()
    var typingJob by remember {
        mutableStateOf<kotlinx.coroutines.Job?>(null)
    }

    LaunchedEffect(recordingState) {
        if (recordingState != RecordingState.IDLE) {
            recordingDuration = 0
            while (true) {
                delay(1000)
                recordingDuration++
            }
        } else {
            recordingDuration = 0
        }
    }

    val formatDuration = { seconds: Int ->
        val m = seconds / 60
        val s = seconds % 60
        String.format("%02d:%02d", m, s)
    }

    val messages by chatViewModel.messages.collectAsState()
    val profile by profileViewModel.profile.collectAsState()
    val conversationDetails by chatViewModel.conversationDetails.collectAsState()
    val onlineUsers by chatViewModel.onlineUsers.collectAsState()
    val lastSeenMap by chatViewModel.lastSeenMap.collectAsState()
    val otherUserLastReadMessageId by chatViewModel.otherUserLastReadMessageId.collectAsState()
    val mediaList by chatViewModel.mediaList.collectAsState()

    LaunchedEffect(onlineUsers.hashCode()) {
        Log.d("OnlineUSERS_DEBUG", onlineUsers.toString())
    }

    val friendId = conversationDetails?.data?.other_user_id
    val isOnline = friendId != null && onlineUsers.contains(friendId)

    val isTyping = friendId != null && typingUsers.contains(friendId)

    val statusText = when {

        isTyping -> "Typing..."

        isOnline -> "Online"

        friendId != null && lastSeenMap[friendId] != null ->
            "Last seen ${formatToTime(lastSeenMap[friendId]!!)}"

        else -> "Offline"
    }

    val listState = rememberLazyListState()

    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val compressed = compressImage(context, it)
            chatViewModel.addImage(compressed)
        }
    }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { chatViewModel.addVideo(it) }
    }

    val recorder = remember { AudioRecorder() }

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) recordingState = RecordingState.IDLE
    }



    fun createMediaUri(): Uri {
        val file = File(
            context.cacheDir,
            "camera_${System.currentTimeMillis()}.jpg"
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    fun createMedia(): Pair<Uri, File> {
        val file = File(
            context.cacheDir,
            "camera_${System.currentTimeMillis()}.jpg"
        )

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        return uri to file
    }

    val scope = rememberCoroutineScope()

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->

        Log.d("CAMERA_DEBUG", "success=$success uri=$photoUri file=$photoFile")

        if (success && photoUri != null) {

            val uri = photoUri!!

            scope.launch {
                try {
                    val compressed = withContext(kotlinx.coroutines.Dispatchers.IO) {
                        compressImage(context, uri)
                    }

                    chatViewModel.addImage(compressed)

                } catch (e: Exception) {
                    Log.e("CAMERA_DEBUG", "Compression failed: ${e.message}")
                }
            }

        } else {
            Log.e("CAMERA_DEBUG", "Image capture failed or null uri")
        }
    }

    val captureVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        if (success && videoUri != null) chatViewModel.addVideo(videoUri!!)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val (uri, file) = createMedia()

            photoUriString = uri.toString()
            photoFile = file

            takePictureLauncher.launch(uri)
        }
    }

    // Mark read on open
    LaunchedEffect(conversationId) {
        if (conversationId != null && conversationId > 0) {
            chatViewModel.markConversationReadSocket(conversationId)
        }
    }

    DisposableEffect(conversationId) {
        onDispose {
            if (conversationId != null && conversationId > 0) {
                chatViewModel.markConversationReadSocket(conversationId)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { recorder.cancel() }
    }

    val prependedCount by chatViewModel.prependedCount.collectAsState()
    val scrollToBottom by chatViewModel.scrollToBottom.collectAsState()

    // 1. Initial load — instant jump
    LaunchedEffect(messages?.conversationId) {
        val size = messages?.messages?.size ?: 0
        if (size > 0) listState.scrollToItem(size - 1)
    }

    // 2. New message — smooth scroll
    LaunchedEffect(scrollToBottom) {
        if (scrollToBottom) {
            val size = messages?.messages?.size ?: 0
            if (size > 0) listState.animateScrollToItem(size - 1)
            chatViewModel.consumeScrollToBottom()
        }
    }

    // 3. Pagination prepend — key-based, just consume
    LaunchedEffect(prependedCount) {
        if (prependedCount > 0) chatViewModel.consumePrependedCount()
    }

    // 4. Pagination trigger
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index == 0 && (messages?.messages?.size ?: 0) > 0) {
                    chatViewModel.loadMoreMessages(conversationId ?: 0)
                }
            }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LGrey),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Transparent),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.widthIn(30.dp, 70.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = Black
                        )
                    }
                    Box {
                        AsyncImage(
                            model = if (conversationDetails?.data?.other_user_profile_image == null)
                                R.drawable.profile_image_placeholder
                            else
                                correctUrl(conversationDetails?.data?.other_user_profile_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .aspectRatio(1f)
                                .clip(HexagonShape),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            painter = painterResource(R.drawable.dot_small_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp),
                            tint = if (isOnline) Green else Black
                        )
                    }
                    Column {
                        Text(
                            text = conversationDetails?.data?.other_user_name ?: "null",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = statusText,
                            fontSize = 12.sp,
                            color = if (isOnline) Green else Color.Gray
                        )
                    }
                }
                Row {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.call_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(-135f)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.video_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(90f)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Column {

                if (replyMessage != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Replying to",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = replyMessage?.content ?: "",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Icon(
                            painter = painterResource(R.drawable.cross_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    chatViewModel.clearReplyMessage()
                                }
                        )
                    }
                }

                // Media preview
                if (mediaList.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        mediaList.forEach { media ->
                            Box(modifier = Modifier.padding(end = 8.dp)) {
                                AsyncImage(
                                    model = media.uri,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                IconButton(
                                    onClick = { chatViewModel.removeMedia(media) },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.cross_svgrepo_com),
                                        contentDescription = null
                                    )
                                }
                                if (media.mediaType == MediaType.VIDEO) {
                                    Text(
                                        text = "🎥",
                                        modifier = Modifier.align(Alignment.BottomStart)
                                    )
                                }
                            }
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(color = LGrey)
                            .padding(all = 4.dp)
                            .fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {}) {
                                Icon(
                                    painter = painterResource(R.drawable.sticker_add_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            IconButton(onClick = { showAttachmentMenu = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.attachment_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }


                        TextField(
                            value = typeMessage,
                            onValueChange = {
                                typeMessage = it
                                if (conversationId != null) {
                                    chatViewModel.startTyping(conversationId)
                                }

                                typingJob?.cancel()

                                typingJob = scope.launch {
                                    delay(1000)

                                    if (conversationId != null) {
                                        chatViewModel.stopTyping(conversationId)
                                    }
                                }
                            },
                            placeholder = { Text("Type") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Transparent,
                                unfocusedIndicatorColor = Transparent,
                                disabledIndicatorColor = Transparent
                            ),
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier.weight(1f)
                        )

                        Box(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (typeMessage.isBlank() && mediaList.isEmpty()) {
                                Icon(
                                    painter = painterResource(R.drawable.mic_svgrepo_com),
                                    contentDescription = "Record Audio",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .pointerInput(Unit) {
                                            awaitEachGesture {
                                                val down = awaitFirstDown()

                                                if (ContextCompat.checkSelfPermission(
                                                        context,
                                                        Manifest.permission.RECORD_AUDIO
                                                    ) != PackageManager.PERMISSION_GRANTED
                                                ) {
                                                    audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                                    return@awaitEachGesture
                                                }

                                                recordingState = RecordingState.RECORDING
                                                val audioPath = recorder.start(context)
                                                dragOffset = androidx.compose.ui.geometry.Offset.Zero

                                                var isCanceledOrLocked = false

                                                do {
                                                    val event = awaitPointerEvent()
                                                    val position = event.changes.first().position
                                                    val drag = position - down.position
                                                    dragOffset = drag

                                                    if (drag.x < -200f) {
                                                        recorder.cancel()
                                                        recordingState = RecordingState.IDLE
                                                        isCanceledOrLocked = true
                                                        break
                                                    } else if (drag.y < -200f) {
                                                        recordingState = RecordingState.LOCKED
                                                        isCanceledOrLocked = true
                                                        break
                                                    }
                                                } while (event.changes.any { it.pressed })

                                                // Normal release → send directly without mediaList
                                                if (!isCanceledOrLocked) {
                                                    val path = recorder.stop()
                                                    recordingState = RecordingState.IDLE

                                                    path?.let {
                                                        val uri = Uri.fromFile(File(it))
                                                        // 👈 sendAudioSilently bypasses _mediaList
                                                        chatViewModel.sendAudioSilently(
                                                            context,
                                                            conversationId!!,
                                                            uri
                                                        )
                                                    }
                                                }
                                            }
                                        },
                                    tint = Black
                                )
                            } else {
                                IconButton(
                                    onClick = {
                                        when {
                                            mediaList.isNotEmpty() -> {
                                                chatViewModel.sendMedia(context, conversationId!!, profile?.data?.id ?: 0)
                                            }
                                            typeMessage.isNotBlank() -> {
                                                chatViewModel.sendMessage(
                                                    conversationId = messages?.conversationId ?: 0,
                                                    text = typeMessage
                                                )
                                                typeMessage = ""
                                                chatViewModel.stopTyping(messages?.conversationId ?: 0)
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.send_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(30.dp)
                                            .rotate(15f)
                                    )
                                }
                            }
                        }
                    }

                    DropdownMenu(
                        expanded = showAttachmentMenu,
                        onDismissRequest = { showAttachmentMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Camera") },
                            onClick = {
                                showAttachmentMenu = false

                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    val (uri, file) = createMedia()

                                    photoUriString = uri.toString()
                                    photoFile = file

                                    takePictureLauncher.launch(uri)

                                } else {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.camera_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier.height(30.dp)
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Photo") },
                            onClick = {
                                showAttachmentMenu = false
                                imagePicker.launch("image/*")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.photo_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier.height(30.dp)
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Video") },
                            onClick = {
                                showAttachmentMenu = false
                                videoPicker.launch("video/*")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.video_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier.height(30.dp)
                        )
                        HorizontalDivider()
                    }

                    // Recording overlay
                    if (recordingState == RecordingState.RECORDING) {
                        Row(
                            modifier = Modifier
                                .matchParentSize()
                                .background(color = LGrey)
                                .padding(end = 50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.dot_small_svgrepo_com),
                                    contentDescription = null,
                                    tint = Color.Red,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = formatDuration(recordingDuration),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.back_svgrepo_com),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(text = " Slide to cancel", color = Color.Gray)
                            }
                        }
                    }

                    // Locked overlay
                    if (recordingState == RecordingState.LOCKED) {
                        Row(
                            modifier = Modifier
                                .matchParentSize()
                                .background(color = LGrey)
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = {
                                recorder.cancel()
                                recordingState = RecordingState.IDLE
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.delete_svgrepo_com),
                                    contentDescription = "Cancel Recording",
                                    tint = Color.Red
                                )
                            }

                            Text(
                                text = formatDuration(recordingDuration),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            LiveWaveform(
                                recorder = recorder,
                                isRecording = recordingState != RecordingState.IDLE
                            )

                            // 👈 Locked send — also uses sendAudioSilently
                            IconButton(onClick = {
                                val path = recorder.stop()
                                recordingState = RecordingState.IDLE
                                path?.let {
                                    val uri = Uri.fromFile(File(it))
                                    chatViewModel.sendAudioSilently(
                                        context,
                                        conversationId!!,
                                        uri
                                    )
                                }
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.send_svgrepo_com),
                                    contentDescription = "Send Recording",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .rotate(15f)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .background(LBlue)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val messageList = messages?.messages ?: emptyList()

            itemsIndexed(
                items = messageList,
                key = { _, msg -> msg.id }
            ) { index, msg ->

                val currentDate = formatToDate2(msg.created_at ?: "")

                val previousDate =
                    if (index > 0)
                        formatToDate2(messageList[index - 1].created_at ?: "")
                    else
                        null

                // 🔥 SHOW DATE SEPARATOR WHEN DATE CHANGES
                if (currentDate != previousDate) {

                    DateSeparator(
                        date = currentDate
                    )
                }

                ChatBubble(
                    message = ChatMessage(
                        message = msg.content ?: "",
                        isUser = msg.sender_id == profile?.data?.id,
                        msgTime = formatToTime2(msg.created_at ?: "12:00 PM")
                    ),

                    isRead = msg.sender_id == profile?.data?.id &&
                            otherUserLastReadMessageId != null &&
                            msg.id <= (otherUserLastReadMessageId ?: 0),

                    onDeleteClick = {
                        chatViewModel.deleteMessage(msg.id)
                    },

                    onReplyClick = {
                        chatViewModel.setReplyMessage(msg)
                    },

                    replyContent = msg.reply_message_content,

                    attachments = msg.attachments.orEmpty()
                )
            }
        }
    }
}

@Composable
fun AudioWavesAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "audio_waves")

    val waves = List(8) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 300 + (index * 100),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "wave_$index"
        )
    }

    Row(
        modifier = modifier.height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        waves.forEach { wave ->
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight(wave.value)
                    .clip(RoundedCornerShape(50))
                    .background(Black)
            )
        }
    }
}

@Composable
fun LiveWaveform(
    recorder: AudioRecorder,
    isRecording: Boolean
) {
    val amplitudes = remember { mutableStateListOf<Float>() }

    LaunchedEffect(isRecording) {
        if (!isRecording) return@LaunchedEffect

        while (isRecording) {
            val raw = recorder.getAmplitude()
            // Boost sensitivity: use sqrt scaling + higher floor
            val amp = if (raw > 0) {
                (Math.sqrt(raw.toDouble()) / Math.sqrt(32767.0)).toFloat()
                    .coerceIn(0.05f, 1f)
            } else 0.05f

            amplitudes.add(amp)
            if (amplitudes.size > 20) amplitudes.removeAt(0)

            delay(80) // 👈 slightly slower = smoother visual
        }
    }

    Row(
        modifier = Modifier
            .height(30.dp)
            .width(80.dp), // fixed width so it doesn't shift layout
        verticalAlignment = Alignment.CenterVertically
    ) {
        amplitudes.forEach { amp ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height((amp * 28).dp.coerceAtLeast(3.dp))
                    .clip(RoundedCornerShape(50))
                    .background(Black)
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

enum class RecordingState {
    IDLE, RECORDING, LOCKED
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(navController = rememberNavController())
}