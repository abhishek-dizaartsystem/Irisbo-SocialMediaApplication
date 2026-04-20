package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.model.FeedPost
import com.example.sociamediaapplication.model.response.PostResponse
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.Post
import com.example.sociamediaapplication.viewmodel.GroupViewModel

@Composable
fun GroupDetailsScreen(
    navController: NavController = rememberNavController(),
    viewModel: GroupViewModel = viewModel(),
    isCreator: Boolean = false,
    onLike: (PostResponse, Int) -> Unit = { post, id -> },
    onOtherProfileClick: (Int) -> Unit = {},
){

    val groupDetails by viewModel.groupDetails.collectAsState()
    val groupMembers by viewModel.groupMembers.collectAsState()

    val groupPosts by viewModel.groupPosts.collectAsState()

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
                AsyncImage(
                    model = if(groupDetails?.group?.cover_image == null) R.drawable.cover_image_placeholder else correctUrl(groupDetails?.group?.cover_image),
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
                                text = groupDetails?.group?.id.toString(),
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
                                AsyncImage(
                                    model = if(groupDetails?.group?.cover_image == null) R.drawable.cover_image_placeholder else correctUrl(groupDetails?.group?.cover_image),
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
                                        text = groupDetails?.group?.name?: "",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = groupDetails?.group?.name?: "",
                                        color = GreyTxt,
                                        fontSize = 14.sp
                                    )

                                    Text(
                                        text = "${groupDetails?.group?.member_count} Members",
                                        color = GreyTxt,
                                        fontSize = 14.sp
                                    )

                                }
                            }
                            Text(
                                text = groupDetails?.group?.description?: "",
                                color = GreyTxt,
                                fontSize = 16.sp
                            )
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.spacedBy(12.dp)
//                            ) {
//                                Button(
//                                    onClick = {
//                                        if(isCreator){
//                                            groupDetails?.group?.id?.let { groupId ->
//                                                navController.navigate(
//                                                    GroupsRoutes.CreateGroupPost.createRoute(groupId)
//                                                )
//                                            }
//                                        }else{
//                                        }
//                                    },
//                                    colors = ButtonDefaults.buttonColors(
//                                        containerColor = Blue
//                                    ),
//                                    modifier = Modifier
//                                        .weight(1f)
//                                        .fillMaxWidth()
//                                ) {
//                                    Text(
//                                        if(isCreator) "Add post" else "Join Group"
//                                    )
//                                }
//                                Button(
//                                    onClick = {
//                                        navController.navigate(
//                                            route = GroupsRoutes.EditGroup.createRoute(groupDetails?.group?.id?: 1)
//                                        )
//                                    },
//                                    colors = ButtonDefaults.buttonColors(
//                                        containerColor = Blue
//                                    ),
//                                    modifier = Modifier
//                                        .weight(1f)
//                                        .fillMaxWidth()
//                                ) {
//                                    Text(
//                                        if(isCreator) "Edit Group" else "Leave Group"
//                                    )
//                                }
//                            }
                        }
                    }
                }
            }
        }

        items(groupPosts?.posts?: emptyList()) {post->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Post(
                    uName = post.username,
                    caption = post.caption,
                    mediaList = post.media,
                    postLikes = post.likes,
                    onLiked = {
                        onLike(
                            PostResponse(
                                id = post.id,
                                user_id = post.user_id,
                                caption = post.caption,
                                media = post.media,
                                media_type = post.mediaType,
                                likes_count = post.likes,
                                tags = post.tags ,
                                created_at = post.created_at,
                                username = post.username,
                                profile_image = post.profile_image,
                                user_reaction = post.user_reaction,
                                is_saved = post.is_saved
                            ),
                            post.id
                        )

                    },
                    onDelete = {
                        viewModel.deleteGroupPost(post.id)
                    },
                    isSaved = post.is_saved,
                    isLiked = post.is_liked,
                    profileImageUrl = correctUrl(post.profile_image),
                    createdAt = post.created_at,
                    type = "group post",
                    onOtherProfileClick = {
                        onOtherProfileClick(post.user_id)
                    }
                )
            }


            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GroupDetailsScreenPreview(){
    GroupDetailsScreen()
}