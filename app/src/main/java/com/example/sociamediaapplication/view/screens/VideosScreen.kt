package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.convertToDuration
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.formatToPostTime
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.VideoThumbnail2
import com.example.sociamediaapplication.viewmodel.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosScreen(
    videoViewModel: VideoViewModel = viewModel(),
    onVideoClick: (Int) -> Unit = {}
) {

    val videos by videoViewModel.videos.collectAsState()

    var searchTxt by remember { mutableStateOf("") }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {

                    TextField(
                        value = searchTxt,

                        onValueChange = { newMessage ->
                            searchTxt = newMessage

                            if (newMessage.isNotBlank()) {
                                videoViewModel.searchVideos(newMessage)
                            }
                        },

                        placeholder = {
                            Text(
                                text = "Search Videos...",
                                color = GreyTxt
                            )
                        },

                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = LGrey,
                            focusedContainerColor = LGrey
                        ),

                        shape = RoundedCornerShape(50.dp),

                        modifier = Modifier
                            .height(54.dp)
                            .fillMaxWidth(),

                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.search_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                tint = GreyTxt
                            )
                        }
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor
                ),
            )
        },

        modifier = Modifier.background(BackgroundColor)

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = BackgroundColor)
        ) {

            HorizontalDivider()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),

                modifier = Modifier
                    .heightIn(200.dp, 2000.dp)
                    .padding(16.dp),

                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                items(videos?.videos ?: emptyList()) { video ->

                    VideoThumbnail2(
                        imageUrl = correctUrl(video.thumbnail_url),
                        text = video.title,
                        channelName = video.creator_name,
                        uploadTime = formatToPostTime(video.created_at),
                        durationTime = convertToDuration(video.duration_seconds),
                        onVideoClick = { onVideoClick(video.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideosScreenPreview(){
    VideosScreen()
}