package com.example.sociamediaapplication.view.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.formatToTime
import com.example.sociamediaapplication.model.ChatMessage
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.ChatBubble
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import java.io.File

//
//val messages = listOf(
//    ChatMessage("Hye Abhishek this side", true),
//    ChatMessage("Hello I am Kartik", false),
//    ChatMessage("Send me the required documents", true),
//    ChatMessage("Okay I'll provide u asap", false),
//    ChatMessage("Thanks", true)
//)



@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    conversationId: Int? = 0
){

    var typeMessage by remember { mutableStateOf("") }
    var showAttachmentMenu by remember { mutableStateOf(false) }

    val messages by chatViewModel.messages.collectAsState()
    val profile by profileViewModel.profile.collectAsState()

    val conversationDetails by chatViewModel.conversationDetails.collectAsState()
    val onlineUsers by chatViewModel.onlineUsers.collectAsState()
    val lastSeenMap by chatViewModel.lastSeenMap.collectAsState()

    val friendId = conversationDetails?.data?.other_user_id

    val isOnline = friendId != null && onlineUsers.contains(friendId)

    val otherUserLastReadMessageId by chatViewModel.otherUserLastReadMessageId.collectAsState()


    val statusText = when {
        isOnline -> "Online"
        friendId != null && lastSeenMap[friendId] != null ->
            "Last seen ${formatToTime(lastSeenMap[friendId]!!)}"
        else -> "Offline"
    }



    val listState = rememberLazyListState()
    var previousSize by remember { mutableStateOf(0) }

//    LaunchedEffect(messages?.messages?.size) {
//
//        val newSize = messages?.messages?.size ?: 0
//
//        if (newSize > previousSize) {
//
//            val addedItems = newSize - previousSize
//
//            if (listState.firstVisibleItemIndex <= 2) {
//                // 🔥 user is near top → preserve scroll (pagination)
//                listState.scrollToItem(addedItems)
//            } else {
//                // 🔥 new message → scroll to bottom
//                listState.animateScrollToItem(newSize - 1)
//            }
//        }
//
//        previousSize = newSize
//    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri?->
        uri?.let{
            chatViewModel.addImage(it)
        }
    }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri?->
        uri?.let{
            chatViewModel.addVideo(it)
        }
    }

//    val documentPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) {uri: Uri? ->
//        uri?.let{
//
//        }
//    }

    val context = LocalContext.current

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val mediaList by chatViewModel.mediaList.collectAsState()

    fun createMediaUri(extension: String): Uri {
        val file = File.createTempFile(
            "media_${System.currentTimeMillis()}",
            extension,
            context.cacheDir
        )
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    /* ---------------- PHOTO CAPTURE ---------------- */

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            chatViewModel.addImage(photoUri!!)
        }
    }

    /* ---------------- VIDEO CAPTURE ---------------- */

    val captureVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        if (success && videoUri != null) {
            chatViewModel.addVideo(videoUri!!)
        }
    }

    /* ---------------- CAMERA PERMISSION ---------------- */

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            // Default open photo
            photoUri = createMediaUri(".jpg")
            takePictureLauncher.launch(photoUri!!)
        }
    }
//
//    val conversationId = conversationDetails?.data?.conversation_id

    DisposableEffect(conversationId) {
        onDispose {
            if (conversationId != null && conversationId > 0) {
                chatViewModel.markConversationReadSocket(conversationId)
            }
//            SocketManager.getSocket()?.off("message:new")

        }
    }

//
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.firstVisibleItemIndex }
//            .collect { index ->
//
//                if (index <= 2) {
//                    chatViewModel.loadMoreMessages(conversationId?:0)
//                }
//            }
//    }

    val prependedCount by chatViewModel.prependedCount.collectAsState()
    val scrollToBottom by chatViewModel.scrollToBottom.collectAsState()

// 1. Initial load — instant jump, no animation
    LaunchedEffect(messages?.conversationId) {
        val size = messages?.messages?.size ?: 0
        if (size > 0) {
            listState.scrollToItem(size - 1)
        }
    }

// 2. New message arrived — smooth scroll to bottom
    LaunchedEffect(scrollToBottom) {
        if (scrollToBottom) {
            val size = messages?.messages?.size ?: 0
            if (size > 0) {
                listState.animateScrollToItem(size - 1)
            }
            chatViewModel.consumeScrollToBottom()
        }
    }

// 3. Older messages prepended — lock viewport, no jump
    LaunchedEffect(prependedCount) {
        if (prependedCount > 0) {
            // With keyed items, LazyColumn maintains position automatically
            // Just consume the signal — no manual scroll needed
            chatViewModel.consumePrependedCount()
        }
    }

// 4. Pagination trigger — only when truly at the top
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index == 0) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
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
                    Box() {
                        AsyncImage(
                            model = if(conversationDetails?.data?.other_user_profile_image == null)
                                        R.drawable.profile_image_placeholder
                                    else
                                        correctUrl(conversationDetails?.data?.other_user_profile_image),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp).aspectRatio(1f).clip(HexagonShape),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            painter = painterResource(R.drawable.dot_small_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp),
                            tint = if (isOnline) Green else Black
                        )
                    }

                    Text(
                        text = conversationDetails?.data?.other_user_name ?: "null",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row() {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.call_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp).rotate(-135f)
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.video_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp).rotate(90f)
                        )
                    }
                }


            }
        },
        bottomBar = {

            Column() {
                // 🔥 MEDIA PREVIEW SECTION
                if (mediaList.isNotEmpty()) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        mediaList.forEach { media ->

                            Box(
                                modifier = Modifier.padding(end = 8.dp)
                            ) {

                                AsyncImage(
                                    model = media.uri,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                // ❌ REMOVE BUTTON (important UX)
                                IconButton(
                                    onClick = {
                                        chatViewModel.removeMedia(media)
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(20.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.cross_svgrepo_com),
                                        contentDescription = null
                                    )
                                }

                                // 🎥 VIDEO LABEL
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

                // 🔥 EXISTING INPUT BAR (unchanged but wrapped)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .background(color = LGrey)
                        .padding(all = 4.dp)
                        .fillMaxWidth()
                ) {
                    Row() {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.sticker_add_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        IconButton(
                            onClick = { showAttachmentMenu = true }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.attachment_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    TextField(
                        value = typeMessage,
                        onValueChange = {newMessage->
                            typeMessage = newMessage
                        },
                        placeholder = {
                            Text("Type")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        trailingIcon = {
                            IconButton(
                                onClick = {

                                    when {

                                        mediaList.isNotEmpty() -> {
                                            chatViewModel.sendMedia(
                                                context,
                                                conversationId!!
                                            )
                                        }

                                        typeMessage.isNotBlank() -> {
                                            chatViewModel.sendMessage(
                                                conversationId = messages?.conversationId ?: 0,
                                                text = typeMessage
                                            )
                                            typeMessage = ""
                                        }
                                    }
                                },
                                modifier = Modifier.padding(end = 4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.send_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .rotate(15f)
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = showAttachmentMenu,
                        onDismissRequest = { showAttachmentMenu = false }
                    ) {

                        DropdownMenuItem(
                            text = { Text("Camera") },
                            onClick = {
                                showAttachmentMenu = false

                                if (
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    photoUri = createMediaUri(".jpg")
                                    takePictureLauncher.launch(photoUri!!)
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
                        DropdownMenuItem(
                            text = { Text("Document") },
                            onClick = {
                                showAttachmentMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.page_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier.height(30.dp)
                        )
                    }


                }
            }

        }
    ) {innerPadding->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .background(LBlue)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(
                items = messages?.messages ?: emptyList(),
                key = { msg -> msg.id }  // 👈 add this
            ){msg->

                ChatBubble(
                    message = ChatMessage(
                        message = msg.content,
                        isUser = msg.sender_id == profile?.data?.id,
                        msgTime = msg.created_at
                    ),
                    isRead = msg.sender_id == profile?.data?.id &&
                            otherUserLastReadMessageId != null &&
                            msg.id <= (otherUserLastReadMessageId ?: 0),
                    onDeleteClick = {
                        chatViewModel.deleteMessage(msg.id)
                    },
                    attachments = msg.attachments
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview(){
    ChatScreen(
        navController = rememberNavController(),
    )
}