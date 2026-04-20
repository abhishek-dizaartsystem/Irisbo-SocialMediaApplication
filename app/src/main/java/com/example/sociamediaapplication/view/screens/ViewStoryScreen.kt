package com.example.sociamediaapplication.view.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
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
    var progress by remember { mutableStateOf(0f) }
    var videoDuration by remember { mutableStateOf<Long?>(null) }
    var isExiting by remember { mutableStateOf(false) }

    val currentStory = stories[currentIndex]
    val mediaUrl = correctUrl2(currentStory.media_url)

    // 🔥 LOAD VIDEO DURATION (safe + cancellable)
    LaunchedEffect(currentIndex, isExiting) {
        if (isExiting) return@LaunchedEffect

        videoDuration = null

        if (currentStory.media_type == "video") {
            videoDuration = getVideoDuration(context, mediaUrl)
        }
    }

    // 🔥 TIMER + AUTO NEXT
    LaunchedEffect(currentIndex, videoDuration, isExiting) {

        if (isExiting) return@LaunchedEffect

        progress = 0f

        val durationMillis = when {
            currentStory.media_type == "video" -> {
                val actual = videoDuration ?: 60_000L
                min(actual, 60_000L)
            }
            else -> 20_000L
        }

        val step = 50L
        val totalSteps = durationMillis / step

        repeat(totalSteps.toInt()) {

            if (isExiting) return@LaunchedEffect

            delay(step)
            progress += 1f / totalSteps
            progress = min(progress, 1f)
        }

        if (currentIndex < stories.lastIndex) {
            currentIndex++
        } else {
            isExiting = true   // 🔥 STOP EVERYTHING
            onFinished(currentStory.id)
        }
    }

    // 🔥 PREVENT UI RECOMPOSITION DURING EXIT
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

            // 🔥 MEDIA
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

            // 🔥 OVERLAY
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.padding(top = 12.dp)
                ) {

                    // 🔥 PROGRESS BARS
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                    ) {
                        items(stories.size) { index ->

                            val barProgress = when {
                                index < currentIndex -> 1f
                                index == currentIndex -> progress
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

                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    AsyncImage(
                        model = correctUrl(stories[0].profile_image),
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