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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.White
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.compose.AsyncImage
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.view.components.Post
import com.example.sociamediaapplication.view.navigation.Routes
import com.example.sociamediaapplication.viewmodel.StoryViewModel

@Composable
fun HomeScreen2(
    mainNavController: NavController = rememberNavController(),
    postViewModel: PostViewModel,
    storyViewModel: StoryViewModel,
    onOtherProfileClick: (Int) -> Unit = {},
    onStoryClick: (Int) -> Unit = {}
) {


    val posts by postViewModel.globalPosts.collectAsState()

    val stories by storyViewModel.stories.collectAsState()

    LaunchedEffect(Unit) {
        postViewModel.loadGlobalPosts()
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
                items(stories?.data ?: emptyList()) {user->
                    Column(
                        modifier = Modifier
                            .height(220.dp)
                            .aspectRatio(0.5f)
                            .clickable{
                                storyViewModel.getSingleUserStories(user.user.id)
                                onStoryClick(user.user.id)
                            }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = if(user.user.profile_image == null)
                                    painterResource(R.drawable.rectangle_6)
                                else
                                    correctUrl(user.user.profile_image),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(220.dp)
                                    .aspectRatio(0.5f),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ){
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AsyncImage(
                                        model = if (user.user.profile_image == null)
                                            painterResource(R.drawable.rectangle_6)
                                        else
                                            correctUrl(user.user.profile_image),
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
                    uName = post.username ?: "",
                    caption = post.caption ?: "",
                    mediaList = post.media,
                    postLikes = post.likes_count ?: 0,
                    onLiked = { postViewModel.toggleGlobalLike(post) },
                    onSaved = { postViewModel.toggleGlobalSave(post) },
                    isSaved = post.is_saved,
                    isLiked = post.user_reaction == "like",
                    profileImageUrl = correctUrl(post.profile_image),
                    createdAt = post.created_at,
                    onOtherProfileClick = { onOtherProfileClick(post.user_id) }
                )
            }

        }
    }
}

