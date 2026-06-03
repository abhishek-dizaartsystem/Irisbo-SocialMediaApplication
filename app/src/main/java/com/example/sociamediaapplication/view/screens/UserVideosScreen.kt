package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.convertToDuration
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.UserVideoItem
import com.example.sociamediaapplication.view.components.VideoPlayerDialog
import com.example.sociamediaapplication.view.components.VideoThumbnail3
import com.example.sociamediaapplication.view.navigation.GroupsRoutes
import com.example.sociamediaapplication.view.navigation.MenuRoutes
import com.example.sociamediaapplication.viewmodel.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserVideosScreen(
    navController: NavController = rememberNavController(),
    videoViewModel: VideoViewModel = viewModel()
){

    var searchTxt by remember { mutableStateOf("") }
    val myVideos by videoViewModel.myVideos.collectAsState()
    var selectedTab by remember {
        mutableStateOf(0)               //0 = My Videos, 1 = Offline Videos
    }
    val downloadedVideos by videoViewModel.downloadedVideos.collectAsState()

    var selectedVideoPath by remember {
        mutableStateOf<String?>(null)
    }

    if (selectedVideoPath != null) {

        VideoPlayerDialog(
            videoUrl = selectedVideoPath!!,
            onDismiss = {
                selectedVideoPath = null
            }
        )
    }

    Scaffold(
        topBar = {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundColor),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = "Videos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Button(
                        onClick = {
                            navController.navigate(MenuRoutes.UploadVideo.route)
                        },
                        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_svgrepo_com),
                            contentDescription = ""
                        )
                        Text(
                            text = "Create",
                            fontSize = 18.sp
                        )
                    }


                }

                Row(
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    TextField(
                        value = searchTxt,
                        onValueChange = {newMessage->
                            searchTxt = newMessage
                        },
                        placeholder = {
                            Text(
                                text = "Search your videos",
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
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                ) {

                    Text(
                        text = "My Videos",
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (selectedTab == 0)
                                    GreyTxt
                                else
                                    Transparent,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                selectedTab = 0
                            }
                            .padding(12.dp),
                        color =
                            if (selectedTab == 0)
                                White
                            else
                                Black,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Offline Videos",
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (selectedTab == 1)
                                    GreyTxt
                                else
                                    Transparent,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                selectedTab = 1
                            }
                            .padding(12.dp),
                        color =
                            if (selectedTab == 1)
                                White
                            else
                                Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }





        },
    ) {innerPadding->
        Column(
            modifier = Modifier
                .background(
                    color = BackgroundColor
                ).fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider()

            if (selectedTab == 0) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement =
                        Arrangement.spacedBy(16.dp),
                    contentPadding =
                        PaddingValues(16.dp)
                ) {

                    items(
                        myVideos?.videos ?: emptyList()
                    ) { video ->

                        UserVideoItem(
                            image_url =
                                correctUrl(video.thumbnail_url),
                            text = video.title,
                            uploadTime = video.created_at,
                            durationTime =
                                convertToDuration(
                                    video.duration_seconds
                                ),
                            likes =
                                video.likes_count.toString(),
                            shares =
                                video.shares_count.toString(),
                            views =
                                video.views_count.toString()
                        )

                        HorizontalDivider()
                    }
                }

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    items(downloadedVideos) { video ->

                        VideoThumbnail3(
                            title = video.title,
                            imageUrl = video.thumbnailUrl,
                            onVideoClick = {
                                // Play offline video
                                selectedVideoPath = video.localPath
                            }
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserVideosScreenPreview(){
    UserVideosScreen()
}