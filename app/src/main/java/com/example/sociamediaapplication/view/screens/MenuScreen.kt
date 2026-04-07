package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.CustomCard
import com.example.sociamediaapplication.view.components.HexagonShape

@Composable
fun MenuScreen(
    onProfile: () -> Unit,
    onMarketplace: () -> Unit,
    onGroups: () -> Unit,
    onUserVideos: () -> Unit,
    onMemories: () -> Unit,
    onPages: () -> Unit,
    onFriends: () -> Unit,
    onEvents: () -> Unit,
    onJobs: () -> Unit,
    onGaming: () -> Unit,
    onVideoAnalytics: () -> Unit,
    onAdvancedSettings: () -> Unit,
    onVideoMonetization: () -> Unit,
    onSettings: () -> Unit,
    profileImg: String?,
    name: String = "John Doe"

){
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Black,
                            shape = HexagonShape
                        )
                        .size(80.dp),
                    shape = HexagonShape
                ) {
                    AsyncImage(
                        model = if(profileImg == null) R.drawable.profile_image_placeholder else "${RetrofitClient.BASE_URL}${profileImg?.removePrefix("/")}",
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(HexagonShape)
                    )

                }
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(
                        text = name,
                        fontSize = 20.sp
                    )
                    Button(
                        onClick = onProfile,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Transparent
                        ),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier.height(24.dp)
                    ) {
                        Text(
                            text = "See your Profile",
                            fontSize = 15.sp,
                            color = GreyTxt
                        )
                    }

                }
                Column(
                    Modifier.fillMaxSize().height(70.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = {
                            onSettings()
                        },
                        Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.settings_logo),
                            contentDescription = "",
                            Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        item {
            Card(
                onClick = {},
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.rectangle_24),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2f),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White)
                            .padding(top = 16.dp, start = 8.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = "Apprentices",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Listed over a week"
                        )
                    }

                    Column(
                        modifier = Modifier.padding(50.dp)
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(40.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Black
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.market_stall_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp),
                                tint = White
                            )
                        }
                    }


                }
            }
            Spacer(modifier = Modifier.height(12.dp))

        }

        item {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomCard(
                        onClicked = onMarketplace,
                        painter = painterResource(R.drawable.market_stall_svgrepo_com),
                        tint = Black,
                        text = "MarketPlace",
                        modifier = Modifier.weight(1f)
                    )
                    CustomCard(
                        onClicked = onGroups,
                        painter = painterResource(R.drawable.team_3),
                        tint = Black,
                        text = "Groups",
                        modifier = Modifier.weight(1f)
                    )
                    CustomCard(
                        onClicked = onUserVideos,
                        painter = painterResource(R.drawable.video_frame_play_horizontal_svgrepo_com),
                        tint = Black,
                        text = "Videos",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 12.dp) ,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomCard(
                        onClicked = onMemories,
                        painter = painterResource(R.drawable.clock_167),
                        tint = Black,
                        text = "Memories",
                        modifier = Modifier.weight(1f)
                    )
                    CustomCard(
                        onClicked = onPages,
                        painter = painterResource(R.drawable.text_pages),
                        tint = Black,
                        text = "Pages",
                        modifier = Modifier.weight(1f)
                    )
                    CustomCard(
                        onClicked = onFriends,
                        painter = painterResource(R.drawable.add_friend_24),
                        tint = Black,
                        text = "Friends",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomCard(
                        onClicked = onEvents,
                        painter = painterResource(R.drawable.text_pages),
                        tint = Black,
                        text = "Events",
                        modifier = Modifier.weight(1f)
                    )
                    CustomCard(
                        onClicked = onJobs,
                        painter = painterResource(R.drawable.briefcase_91),
                        tint = Black,
                        text = "Jobs",
                        modifier = Modifier.weight(1f)
                    )
                    CustomCard(
                        onClicked = onGaming,
                        painter = painterResource(R.drawable.game_165),
                        tint = Black,
                        text = "Gaming",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomCard(
                        onClicked = onVideoAnalytics,
                        painter = painterResource(R.drawable.up_trend_round_svgrepo_com),
                        tint = Black,
                        text = "Video Analytics",
                        modifier = Modifier.weight(1f)
                    )


                    CustomCard(
                        onClicked = onAdvancedSettings,
                        painter = painterResource(R.drawable.advanced_options_svgrepo_com),
                        tint = Black,
                        text = "Advanced Settings",
                        modifier = Modifier.weight(1f)
                    )


                    CustomCard(
                        onClicked = onVideoMonetization,
                        painter = painterResource(R.drawable.rupee_sign_svgrepo_com),
                        tint = Black,
                        text = "Video Monetization",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }





    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuScreenPreview(){
    MenuScreen(
        onProfile = {},
        onMarketplace = {},
        onGroups = {},
        onUserVideos = {},
        onMemories = {},
        onPages = {},
        onFriends = {},
        onEvents = {},
        onJobs = {},
        onGaming = {},
        onVideoAnalytics = {},
        onAdvancedSettings = {},
        onVideoMonetization = {},
        onSettings = {},
        profileImg = "R.drawable.rectangle_5"
    )
}