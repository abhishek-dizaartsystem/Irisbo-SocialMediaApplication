package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.VideoThumbnail

@Composable
fun VideosScreen(){

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
            Row(
                modifier = Modifier.padding(8.dp)
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
    ) {innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
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
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideosScreenPreview(){
    VideosScreen()
}