package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.UserVideoItem
import com.example.sociamediaapplication.view.components.VideoThumbnail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserVideosScreen(){

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

    var searchTxt by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        TextField(
                            value = searchTxt,
                            onValueChange = {newMessage->
                                searchTxt = newMessage
                            },
                            placeholder = {
                                Text("Search videos...")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Transparent,
                                unfocusedIndicatorColor = Transparent,
                                disabledIndicatorColor = Transparent,
                                unfocusedContainerColor = Transparent,
                                focusedContainerColor = Transparent
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 3.dp,
                                    color = Blue,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.search_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor
                )
            )


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
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // IMPORTANT: prevent infinite height
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(videoList) { thumbnailImage ->
                    UserVideoItem(
                        painter = painterResource(thumbnailImage)
                    )
                    HorizontalDivider()
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