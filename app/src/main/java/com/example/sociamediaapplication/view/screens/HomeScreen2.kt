package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.FeedPost
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.Post
import com.example.sociamediaapplication.view.navigation.Routes

@Composable
fun HomeScreen2(
    mainNavController: NavController = rememberNavController()
){

    val posts = remember {
        mutableStateListOf(
            FeedPost("1", "John", "Vintage vibes", isFollowing = false, isLiked = false, likes = 20),
            FeedPost("2", "Kartik", "Hello world", isFollowing = true, isLiked = false, likes = 45),
            FeedPost("3", "Aman", "Compose is powerful", isFollowing = false,isLiked = false, likes = 12),
            FeedPost("4", "Rohit", "Summer time", isFollowing = false, isLiked = false, likes = 9)
        )
    }

    LazyColumn(
        modifier = Modifier
            .background(
                color = BackgroundColor
            ).fillMaxSize()
    ) {
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .height(220.dp)
                            .aspectRatio(0.5f)
                            .clickable{
                                mainNavController.navigate(Routes.EditStatus.route)
                            }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()){
                            Image(
                                painter = painterResource(R.drawable.rectangle_5),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(220.dp)
                                    .aspectRatio(0.5f),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    modifier = Modifier.background(
                                        color = Blue,
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.add_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.height(40.dp),
                                        tint = White
                                    )
                                }
                                Text(
                                    text = "Create Story",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
                items(4) {
                    Column(
                        modifier = Modifier
                            .height(220.dp)
                            .aspectRatio(0.5f)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(R.drawable.rectangle_6),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(220.dp)
                                    .aspectRatio(0.5f),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ){
                                Image(
                                    painter = painterResource(R.drawable.scene1),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .aspectRatio(1f)

                                        .border(
                                            2.dp,
                                            Blue,
                                            CircleShape
                                        ),
                                    contentScale = ContentScale.Crop


                                )
                            }
                        }
                    }
                }
            }
        }
        items(posts.size) {index->

            val post = posts[index]

            Column(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
            ) {
                Post(
                    caption = post.caption,
                    isFollowing = post.isFollowing,
                    onFollow = {
                        posts[index] = post.copy(
                            isFollowing = !post.isFollowing
                        )
                    },
                    postLikes = post.likes,
                    isLiked = post.isLiked,

                    onLiked = {
                        posts[index] = post.copy(
                            isLiked = !post.isLiked,
                            likes = if (post.isLiked)
                                post.likes - 1
                            else
                                post.likes + 1
                        )
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen2Preview(){
    HomeScreen2()
}
