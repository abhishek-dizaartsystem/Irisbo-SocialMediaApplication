package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.CommentItem
import com.example.sociamediaapplication.view.components.DescriptionBottomSheet
import com.example.sociamediaapplication.view.components.VideoFrame
import com.example.sociamediaapplication.view.components.VideoThumbnail

@Composable
fun VideoPlayScreen() {

    var showDescriptionBottomSheet by remember { mutableStateOf(false) }

    val videoList = listOf(
        R.drawable.rectangle_5,
        R.drawable.rectangle_36,
        R.drawable.rectangle_24,
        R.drawable.rectangle_58,
        R.drawable.rectangle_6,
        R.drawable.rectangle_37,
        R.drawable.rectangle_36__2_,
        R.drawable.rectangle_36__1_,
        R.drawable.rectangle_38,
        R.drawable.rectangle_39
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Top Video Player (Not Scrollable)
        VideoFrame()

        // Everything below scrolls
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {

            // Title
            item {
                Text(
                    text = "Wareen buffet-full Recipie",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Views Row
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "2K views",
                        color = GreyTxt
                    )
                    Text(
                        text = "10 days ago",
                        modifier = Modifier.padding(start = 6.dp),
                        color = GreyTxt
                    )
                    Button(
                        onClick = {showDescriptionBottomSheet=true},
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(20.dp).padding(start = 6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                    ){
                        Text(
                            text = "more",
                            modifier = Modifier.padding(start = 6.dp),
                            color = GreyTxt
                        )
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "",
                            tint = GreyTxt
                        )
                    }
                }
            }

            // Action Buttons Row
            item {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(R.drawable.like_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
                        Text(
                            text = "24",
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(R.drawable.like_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp).rotate(180f),
                            tint = Black
                        )
                        Text(
                            text = "24",
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(R.drawable.share_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
                        Text(
                            text = "24",
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(R.drawable.save_icon),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
                        Text(
                            text = "24",
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                }
            }

            // Divider
            item {
                Spacer( modifier = Modifier.height(8.dp) )
                Spacer( modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Grey, shape = RoundedCornerShape(4.dp))
                    .padding(vertical = 4.dp) )
                Spacer( modifier = Modifier.height(12.dp) )
            }

            // Channel Section
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.rectangle_5),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )

                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Dr Heric Burg",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "5.7K Subscribers",
                            color = GreyTxt,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Black)
                    ) {
                        Text(text = "Subscribe")
                    }
                }
            }

            item {
                Spacer( modifier = Modifier.height(8.dp) )
                Spacer( modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Grey, shape = RoundedCornerShape(4.dp))
                    .padding(vertical = 4.dp) )
                Spacer( modifier = Modifier.height(12.dp) )
            }

            // Comments Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "4355 Comments",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.filter_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = GreyTxt
                        )
                        Text(
                            text = "Sort by",
                            fontSize = 14.sp,
                            color = GreyTxt,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // Comments List
            items(2) {
                CommentItem()
            }

            item {
                Spacer( modifier = Modifier.height(8.dp) )
                Spacer( modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Grey, shape = RoundedCornerShape(4.dp))
                    .padding(vertical = 4.dp) )
                Spacer( modifier = Modifier.height(12.dp) )
            }

            // Related Videos Grid
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .heightIn(200.dp, 2000.dp), // IMPORTANT: prevent infinite height
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(videoList) { thumbnailImage ->
                        VideoThumbnail(
                            painter = painterResource(thumbnailImage)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    if (showDescriptionBottomSheet) {
        DescriptionBottomSheet(
            onDismiss = {showDescriptionBottomSheet = false}
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideoPlayScreenPreview(){
    VideoPlayScreen()
}
