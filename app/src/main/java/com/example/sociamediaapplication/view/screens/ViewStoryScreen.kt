package com.example.sociamediaapplication.view.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.correctUrl2
import com.example.sociamediaapplication.data.utils.getVideoDuration
import com.example.sociamediaapplication.model.StoryUi
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.AutoVideo
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.StoryViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewStoryScreen(
    storyViewModel: StoryViewModel,
    isUserStory: Boolean = false,
    onFinished: () -> Unit = {},
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val context = LocalContext.current

    val userStories by storyViewModel.userStories.collectAsState()
    val myStories by storyViewModel.myStories.collectAsState()
    val viewers by storyViewModel.storyViewers.collectAsState()
    val profile by profileViewModel.profile.collectAsState()

    var wasPausedPress by remember { mutableStateOf(false) }

    val stories = remember(userStories, myStories, isUserStory) {
        if (isUserStory) {
            (myStories?.data ?: emptyList()).map {
                StoryUi(
                    id = it.id,
                    mediaUrl = correctUrl2(it.media_url),
                    mediaType = it.media_type,
                    profileImage = profile?.data?.profile_image,
                    userName = profile?.data?.name ?: "null"
                )
            }
        } else {
            (userStories?.data?.stories ?: emptyList()).map {
                StoryUi(
                    id = it.id,
                    mediaUrl = correctUrl2(it.media_url),
                    mediaType = it.media_type,
                    profileImage = it.profile_image,
                    userName = it.name
                )
            }
        }
    }

    val sheetState = rememberModalBottomSheetState()
    var showViewersSheet by remember { mutableStateOf(false) }

    if (stories.isEmpty()) return

    var currentIndex by remember { mutableStateOf(0) }
    var videoDuration by remember { mutableStateOf<Long?>(null) }
    var isExiting by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    val currentStory = stories[currentIndex]

    val progressAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    // 🔥 Video duration
    LaunchedEffect(currentIndex) {
        videoDuration = null
        if (currentStory.mediaType == "video") {
            videoDuration = getVideoDuration(context, currentStory.mediaUrl)
        }
    }

    // 🔥 Fetch viewers
    LaunchedEffect(currentIndex, isUserStory) {
        if (isUserStory) {
            storyViewModel.getStoryViewers(currentStory.id)
        }
    }

    val durationMillis = when {
        currentStory.mediaType == "video" -> min(videoDuration ?: 60000L, 60000L)
        else -> 20000L
    }

    // ✅ RESET (fix carry-over)
    LaunchedEffect(currentIndex) {
        progressAnim.stop()
        progressAnim.snapTo(0f)
    }

    // ✅ ANIMATION (pause aware)
    LaunchedEffect(currentIndex, durationMillis, isPaused) {

        if (isExiting) return@LaunchedEffect

        if (!isPaused) {
            isAnimating = true
            progressAnim.animateTo(
                1f,
                tween(durationMillis.toInt())
            )
        } else {
            progressAnim.stop()
            isAnimating = false
        }
    }

    // ✅ TIMER (pause aware, fresh start)
    LaunchedEffect(currentIndex, durationMillis, isPaused) {

        if (isExiting) return@LaunchedEffect

        var elapsed = 0L

        while (elapsed < durationMillis) {
            if (!isPaused) {
                delay(50)
                elapsed += 50
            } else {
                delay(50)
            }
        }

        if (currentIndex < stories.lastIndex) {
            currentIndex++
        } else {
            isExiting = true
            onFinished()
        }
    }

    // 🔥 Mark viewed
    LaunchedEffect(currentIndex, stories) {
        if (stories.isEmpty()) return@LaunchedEffect
        storyViewModel.markStoryViewed(stories[currentIndex].id)
    }

    if (isExiting) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectVerticalDragGestures(        // 🆕 swipe down to exit
                        onVerticalDrag = { _, dragAmount ->
                            if (dragAmount > 60f) {    // swipe down threshold
                                isExiting = true
                                onFinished()
                                navController.popBackStack()
                            }
                        }
                    )
                }
                .pointerInput(Unit) {
                    coroutineScope {
                        detectTapGestures(

                            onPress = {
                                wasPausedPress = false
                                val job = launch {
                                    delay(500)           // if held longer than 150ms → it's a pause, not a tap
                                    wasPausedPress = true
                                    isPaused = true
                                }
                                tryAwaitRelease()
                                job.cancel()            // cancelled if released before 150ms (it was a tap)
                                isPaused = false
                            },

                            onTap = { offset ->
                                if (wasPausedPress) {   // 🔥 skip navigation if it was a hold
                                    wasPausedPress = false
                                    return@detectTapGestures
                                }
                                val width = size.width

                                if (offset.x < width / 2) {
                                    if (currentIndex > 0) currentIndex--
                                } else {
                                    if (currentIndex < stories.lastIndex) {
                                        currentIndex++
                                    } else {
                                        isExiting = true
                                        onFinished()
                                    }
                                }
                            }
                        )
                    }

                }
        ) {

            val isVideo = currentStory.mediaType == "video"

            key(currentIndex) {
                if (isVideo) {
                    AutoVideo(
                        url = currentStory.mediaUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(9f / 16f),
                        isPaused = isPaused
                    )
                } else {
                    AsyncImage(
                        model = currentStory.mediaUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(9f / 16f)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    ) {
                        stories.forEachIndexed { index, _ ->

                            val progress = when {
                                index < currentIndex -> 1f
                                index == currentIndex -> if (isAnimating) progressAnim.value else 0f
                                else -> 0f
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(Color.White.copy(alpha = 0.3f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(progress)
                                        .background(Color.White)
                                )
                            }

                            if (index != stories.lastIndex) {
                                Spacer(Modifier.width(4.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    var menuExpanded by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = correctUrl(currentStory.profileImage),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .size(40.dp)
                                    .aspectRatio(1f)
                                    .clip(HexagonShape),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = currentStory.userName,
                                color = White
                            )
                        }

                        if (isUserStory) {

                            Box {

                                IconButton(onClick = { menuExpanded = true }) {
                                    Icon(
                                        painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                                        contentDescription = null,
                                        modifier = Modifier.rotate(90f).size(30.dp),
                                        tint = White,
                                    )
                                }

                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false }
                                ) {

                                    DropdownMenuItem(
                                        text = { Text("Delete") },
                                        onClick = {
                                            menuExpanded = false
                                            storyViewModel.deleteStory(currentStory.id)
                                            navController.popBackStack()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                if (isUserStory) {
                    Text(
                        text = "Views: ${viewers?.data?.size ?: 0}",
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { showViewersSheet = true }
                    )
                } else {
                    Column {}
                }
            }
        }
    }

    if (showViewersSheet) {

        ModalBottomSheet(
            onDismissRequest = { showViewersSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {

            val viewerList = viewers?.data ?: emptyList()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text("Viewers", color = Color.Black)

                viewerList.forEach { viewer ->
                    Row(Modifier.padding(vertical = 8.dp)) {
                        AsyncImage(
                            model = correctUrl(viewer.profile_image),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp).clip(HexagonShape)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(viewer.name)
                            Text(viewer.viewed_at, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}