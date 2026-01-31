package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.TTransparentWhite
import com.example.sociamediaapplication.ui.theme.TransparentWhite
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun Post(
    uName: String = "John Doe",
    sincePosted: String = "1m",
    isVerified: Boolean = false,
    caption: String = "",
    mediaList: List<Int> = listOf(
        (R.drawable.rectangle_6),
        (R.drawable.rectangle_5),
        (R.drawable.rectangle_24)
    )
){
    val size = mediaList.size

    val pagerState = rememberPagerState(pageCount = {mediaList.size})
    Card(
        colors = CardDefaults
            .cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { /* Navigate to profile */ },
                        modifier = Modifier.size(50.dp) // Set the size of the clickable area
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rectangle_5),
                            contentDescription = "Profile Image",
                            // This crops the image into a square before clipping to a circle
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp) // Ensure the image fills the button
                                .clip(CircleShape) // Makes it perfectly circular
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = uName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Icon(
                                painter = painterResource(R.drawable.verified_check_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(2.dp),
                                tint = Blue
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = sincePosted
                            )
                            Icon(
                                painter = painterResource(R.drawable.world_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = 4.dp)
                            )
                        }

                    }
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            //Caption
            Text(
                text = caption
            )
            Box(
                modifier = Modifier.aspectRatio(1f)
            ){
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) { page ->

                    Image(
                        painter = painterResource(mediaList[page]),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {}
                    Row (
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // 🔥 Smart Dots Indicator
                        if (mediaList.size > 1) {

                            val totalDots = mediaList.size
                            val currentPage = pagerState.currentPage
                            val maxVisibleDots = 5

                            val startIndex = when {
                                totalDots <= maxVisibleDots -> 0
                                currentPage <= 2 -> 0
                                currentPage >= totalDots - 3 -> totalDots - maxVisibleDots
                                else -> currentPage - 2
                            }

                            val endIndex = minOf(startIndex + maxVisibleDots, totalDots)



                            Row(
                                modifier = Modifier
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                val hasPrevious = startIndex > 0
                                val hasNext = endIndex < totalDots

                                for (i in startIndex until endIndex) {

                                    val dotSize = when {
                                        i == currentPage -> 10.dp
                                        i == startIndex && hasPrevious -> 4.dp
                                        i == endIndex - 1 && hasNext -> 4.dp
                                        else -> 7.dp
                                    }

                                    Box(
                                        modifier = Modifier
                                            .size(dotSize)
                                            .clip(CircleShape)
                                            .background(
                                                if (i == currentPage)
                                                    White
                                                else
                                                    White.copy(alpha = 0.5f)
                                            )
                                    )
                                }

                            }
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(
                                    color = TTransparentWhite,
                                    shape = RoundedCornerShape(12.dp))
                                .width(40.dp)
                                .height(20.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${pagerState.currentPage+1}/$size")
                        }
                    }
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Row(
                    modifier = Modifier
                        .width(140.dp)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { /* Navigate to profile */ },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(0.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.like_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                    IconButton(
                        onClick = { /* Navigate to profile */ },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(0.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.comment_outlined_3_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                    IconButton(
                        onClick = { /* Navigate to profile */ },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(0.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.send_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PostPreview(){
    Post(caption = "Springs bright ,all sustainable !" +
            "Everything shown was made before 1982,except vintage")
}