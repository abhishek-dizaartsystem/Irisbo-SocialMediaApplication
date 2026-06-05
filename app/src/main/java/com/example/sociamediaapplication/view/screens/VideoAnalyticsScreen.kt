package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.sociamediaapplication.model.VideoAnalyticsCardModel
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Purple
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.AgeDistributionCard
import com.example.sociamediaapplication.view.components.TopVideoCard
import com.example.sociamediaapplication.view.components.VideoAnalyticsCard
import com.example.sociamediaapplication.view.components.VideoAnalyticsItem
import com.example.sociamediaapplication.view.components.VideoPerformanceItem
import com.example.sociamediaapplication.viewmodel.AnalyticsViewModel

@Composable
fun VideoAnalyticsScreen(
    navController: NavController = rememberNavController(),
    analyticsViewModel: AnalyticsViewModel = viewModel()
){

    val channelAnalytics by analyticsViewModel.channelAnalytics.collectAsState()
    val videoDashboard by analyticsViewModel.videoDashboard.collectAsState()
    val topVideos by analyticsViewModel.topVideos.collectAsState()


    val analyticsItems = listOf(
        VideoAnalyticsCardModel(
            icon = R.drawable.eye_outlined_svgrepo_com,
            iconTint = Blue,
            value = (channelAnalytics?.data?.total_views_count?:0).toString(),
            name = "Total Views"
        ),
        VideoAnalyticsCardModel(
            icon = R.drawable.heart_svgrepo_com,
            iconTint = Red,
            value = (channelAnalytics?.data?.total_likes_count?:0).toString(),
            name = "Total Likes"
        ),
        VideoAnalyticsCardModel(
            icon = R.drawable.chat_dots_svgrepo_com,
            iconTint = Green,
            value = (channelAnalytics?.data?.total_comments_count?:0).toString(),
            name = "Comments"
        ),
        VideoAnalyticsCardModel(
            icon = R.drawable.share_svgrepo_com,
            iconTint = Purple,
            value = (channelAnalytics?.data?.total_shares_count?:0).toString(),
            name = "Shares"
        )
    )

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
            ) {

                Spacer(modifier = Modifier.height(6.dp))
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
                        text = "Video Analytics ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Row(modifier = Modifier.width(16.dp)) {
                    }




                }

                Spacer(
                    modifier = Modifier
                        .background(color = Grey)
                        .height(1.dp)
                        .fillMaxWidth()
                )

            }
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = BackgroundColor
                )
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 🔹 GRID SECTION (2 columns)
                items(analyticsItems.chunked(2)) { rowItems ->

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        rowItems.forEach { item ->
                            VideoAnalyticsCard(
                                icon = painterResource(item.icon),
                                iconTint = item.iconTint,
                                value = item.value,
                                name = item.name,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // if odd count, fill empty space
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                item {

                    Text(
                        text = "Top Performing Videos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                items(
                    topVideos?.data ?: emptyList()
                ) { video ->

                    TopVideoCard(
                        video = video
                    )
                }

                item {

                    Text(
                        text = "Video Performance",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                items(
                    videoDashboard
                        ?.data
                        ?.videos
                        ?.data
                        ?: emptyList()
                ) { video ->

                    VideoPerformanceItem(
                        video = video
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideoAnalyticsScreenPreview(){
    VideoAnalyticsScreen()
}