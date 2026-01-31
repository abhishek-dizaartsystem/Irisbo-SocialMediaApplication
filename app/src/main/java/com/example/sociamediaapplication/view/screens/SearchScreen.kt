package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.TrendingCreator

@Composable
fun SearchScreen(){

    var searchTxt by remember { mutableStateOf("") }

    var trendingCreatorsList = remember { listOf(
        (R.drawable.rectangle_58),
        (R.drawable.rectangle_5),
        (R.drawable.rectangle_38),
        (R.drawable.rectangle_6),
        (R.drawable.rectangle_37),
        (R.drawable.rectangle_36__1_)
    ) }

    val explorePosts = remember { listOf(
        (R.drawable.rectangle_58),
        (R.drawable.rectangle_5),
        (R.drawable.rectangle_38),
        (R.drawable.rectangle_6),
        (R.drawable.rectangle_37),
        (R.drawable.rectangle_36__1_),
        (R.drawable.rectangle_36__2_),
        (R.drawable.rectangle_24),
        (R.drawable.rectangle_37)
    )}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        TextField(
            value = searchTxt,
            onValueChange = {newMessage->
                searchTxt = newMessage
            },
            placeholder = {
                Text("Search User, Posts...")
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
                )
                .padding(top = 4.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        )

        Text(
            text = "Trending Creators",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyRow() {
            items(trendingCreatorsList){img->
                TrendingCreator(
                    painter = painterResource(img)
                )
            }
        }
        Text(
            text = "Explore",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp, top = 20.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 Columns
            modifier = Modifier
                .fillMaxSize()
                .background(LGrey),
            contentPadding = PaddingValues(1.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(explorePosts) { postImage ->
                Image(
                    painter = painterResource(id = postImage),
                    contentDescription = "Post",
                    modifier = Modifier
                        .aspectRatio(1f) // Makes it a perfect square
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview(){
    SearchScreen()
}