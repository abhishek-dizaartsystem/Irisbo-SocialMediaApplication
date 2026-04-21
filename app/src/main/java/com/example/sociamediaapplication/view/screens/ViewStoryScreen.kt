package com.example.sociamediaapplication.view.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.correctUrl2
import com.example.sociamediaapplication.data.utils.getVideoDuration
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.AutoVideo
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.viewmodel.StoryViewModel
import kotlinx.coroutines.delay
import kotlin.math.min

@Composable
fun ViewStoryScreen(
    storyViewModel: StoryViewModel = viewModel(),
    onFinished: (Int) -> Unit
) {

    val context = LocalContext.current
    val userStories by storyViewModel.userStories.collectAsState()
    val stories = userStories?.data?.stories ?: emptyList()

    if (stories.isEmpty()) return

    var currentIndex by remember { mutableStateOf(0) }
    var videoDuration by remember { mutableStateOf<Long?>(null) }
    var isExiting by remember { mutableStateOf(false) }

    val currentStory = stories[currentIndex]
    val mediaUrl = remember(currentIndex) {
        correctUrl2(currentStory.media_url)
    }

    val progressAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    // 🔥 Load video duration
    LaunchedEffect(currentIndex) {
        videoDuration = null
        if (currentStory.media_type == "video") {
            videoDuration = getVideoDuration(context, mediaUrl)
        }
    }

    // 🔥 Duration logic (IMPORTANT)
    val durationMillis = when {
        currentStory.media_type == "video" -> {
            val actual = videoDuration ?: 60_000L
            min(actual, 60_000L)
        }
        else -> 20_000L
    }

    // 🔥 Progress animation (NO FLICKER)
    LaunchedEffect(currentIndex, durationMillis) {

        if (isExiting) return@LaunchedEffect

        isAnimating = false

        progressAnim.stop()
        progressAnim.snapTo(0f)

        isAnimating = true

        progressAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis.toInt())
        )
    }

    // 🔥 Navigation + video stop logic
    LaunchedEffect(currentIndex, durationMillis) {

        if (isExiting) return@LaunchedEffect

        delay(durationMillis)

        if (!isExiting) {
            if (currentIndex < stories.lastIndex) {
                currentIndex++   // 🔥 triggers recomposition → video stops
            } else {
                isExiting = true
                onFinished(currentStory.id)
            }
        }
    }

    if (isExiting) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (isExiting) return@clickable

                    if (currentIndex < stories.lastIndex) {
                        currentIndex++
                    } else {
                        isExiting = true
                        onFinished(currentStory.id)
                    }
                },
            contentAlignment = Alignment.Center
        ) {

            val isVideo = currentStory.media_type == "video"

            // 🔥 KEY FIX: force video recreation (prevents looping carryover)
            key(currentIndex) {
                if (isVideo) {
                    AutoVideo(
                        url = mediaUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(9f / 16f)
                    )
                } else {
                    AsyncImage(
                        model = mediaUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(9f / 16f),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
            }

            // 🔥 OVERLAY
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.padding(top = 12.dp)
                ) {

                    // 🔥 PROGRESS BAR
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                    ) {
                        stories.forEachIndexed { index, _ ->

                            val barProgress = when {
                                index < currentIndex -> 1f
                                index == currentIndex -> {
                                    if (isAnimating) progressAnim.value else 0f
                                }
                                else -> 0f
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(White.copy(alpha = 0.3f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(barProgress)
                                        .background(White)
                                )
                            }

                            if (index != stories.lastIndex) {
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val profileImage = remember(stories) {
                        correctUrl(stories[0].profile_image)
                    }
                    AsyncImage(
                        model = profileImage,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(45.dp)
                            .clip(HexagonShape),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }

                Column {}
            }
        }
    }
}