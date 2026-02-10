package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.FeedPost
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.TransparentBlack
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.Post
import com.example.sociamediaapplication.view.navigation.GroupsRoutes

@Composable
fun GroupScreen(
    painter: Painter = painterResource(R.drawable.travel),
    groupId: String = "1",
    navController: NavController = rememberNavController()
){

    val posts = remember {
        mutableStateListOf(
            FeedPost("1", "John", "Vintage vibes", false, false, 20),
            FeedPost("2", "Kartik", "Hello world", true, false, 45),
            FeedPost("3", "Aman", "Compose is powerful", false, false, 12),
            FeedPost("4", "Rohit", "Summer time", false, false, 9)
        )
    }

    LazyColumn(
        modifier = Modifier.background(BackgroundColor)
    ) {
        item {
            Box(){
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = DTransparentBlack
                            ),
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.back_svgrepo_com),
                                contentDescription = "",
                                tint = White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = groupId,
                                color = Black
                            )
                            IconButton(
                                onClick = {},
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = DTransparentBlack
                                ),
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.share_svgrepo_com),
                                    contentDescription = "",
                                    tint = White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(
                                onClick = {},
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = DTransparentBlack
                                ),
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                                    contentDescription = "",
                                    tint = White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        ),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Image(
                                    painter = painter,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "React Developers",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "Technology",
                                        color = GreyTxt,
                                        fontSize = 14.sp
                                    )

                                    Text(
                                        "28K followers",
                                        color = GreyTxt,
                                        fontSize = 14.sp
                                    )

                                }
                            }
                            Text(
                                text = "Stunning photography, tips & tricks, and gear reviews.",
                                color = GreyTxt,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Blue
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                ) {
                                    Text("Join Group")
                                }
                                Button(
                                    onClick = {
                                        navController.navigate(
                                            route = GroupsRoutes.EditGroup.createRoute(groupId)
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Blue
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                ) {
                                    Text("Edit Group")
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
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
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
            }


            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GroupScreenPreview(){
    GroupScreen()
}