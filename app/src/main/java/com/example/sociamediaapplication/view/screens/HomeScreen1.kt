package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.model.FeedPost
import com.example.sociamediaapplication.view.components.Post

@Composable
fun HomeScreen1(){

    val posts = remember {
        mutableStateListOf(
            FeedPost("1", "John", "Vintage vibes", false, false, 20),
            FeedPost("2", "Kartik", "Hello world", true, false, 45),
            FeedPost("3", "Aman", "Compose is powerful", false, false, 12),
            FeedPost("4", "Rohit", "Summer time", false, false, 9)
        )
    }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {

        items(posts.size) {index->
            val post = posts[index]
            Post(
                uName = post.userName,
                caption = post.caption,
                isFollowing = post.isFollowing,
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
                },

                onFollow = {
                    posts[index] = post.copy(
                        isFollowing = !post.isFollowing
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen1Preview(){
    HomeScreen1()
}
