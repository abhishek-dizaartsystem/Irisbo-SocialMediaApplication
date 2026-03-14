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
import com.example.sociamediaapplication.view.components.VideoAnalyticsCard
import com.example.sociamediaapplication.view.components.VideoAnalyticsItem

@Composable
fun VideoAnalyticsScreen(
    navController: NavController = rememberNavController()
){

    var videosSelected by remember { mutableStateOf(false) }

    var analyticsItems = remember { mutableStateListOf(
        VideoAnalyticsCardModel(
            icon = R.drawable.eye_outlined_svgrepo_com,
            iconTint = Blue,
            value = "124.5K",
            name = "Total Views"
        ),
        VideoAnalyticsCardModel(
            icon = R.drawable.heart_svgrepo_com,
            iconTint = Red,
            value = "8.5K",
            name = "Total Likes"
        ),
        VideoAnalyticsCardModel(
            icon = R.drawable.chat_dots_svgrepo_com,
            iconTint = Green,
            value = "1.4K",
            name = "Comments"
        ),
        VideoAnalyticsCardModel(
            icon = R.drawable.share_svgrepo_com,
            iconTint = Purple,
            value = "824",
            name = "Shares"
        )
    ) }

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
                item{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = LGrey,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(!videosSelected) LGrey else White
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(0.5f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Videos",
                                    color = if(!videosSelected) GreyTxt else Black,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(videosSelected) LGrey else White
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Audience",
                                    color = if(videosSelected) GreyTxt else Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                if(videosSelected){
                    item {

                        Text(
                            text = "Top Performing Videos",
                            color = GreyTxt,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                    items(8){
                        VideoAnalyticsItem()
                    }
                }else{
                    item {
                        AgeDistributionCard()
                    }
                    item{
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = White
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.clock_three_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Watch Time",
                                        modifier = Modifier.padding(start = 8.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "2.4K hrs",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = "Watch Time",
                                    color = GreyTxt
                                )
                            }
                        }
                    }
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