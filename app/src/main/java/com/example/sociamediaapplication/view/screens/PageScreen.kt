package com.example.sociamediaapplication.view.screens

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.sociamediaapplication.data.utils.formatPostTime
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.PagePost
import com.example.sociamediaapplication.view.components.Post
import com.example.sociamediaapplication.viewmodel.PageViewModel

@Composable
fun PageScreen(
    painter: Painter = painterResource(R.drawable.react_laptop),
    pageId: Int = 1,
    navController: NavController = rememberNavController(),
    viewModel: PageViewModel = viewModel()
){

    val pagePosts by viewModel.pagePosts.collectAsState()

    val pageDetails by viewModel.pageDetails.collectAsState()


    LazyColumn(
        modifier = Modifier.background(BackgroundColor)
    ) {
        item {
            Box(){
                AsyncImage(
                    model = if(pageDetails?.data?.cover_image == null) R.drawable.cover_image_placeholder
                    else correctUrl(pageDetails?.data?.cover_image),
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
                                    model = if(pageDetails?.data?.profile_image == null) R.drawable.profile_image_placeholder
                                    else correctUrl(pageDetails?.data?.profile_image),
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
                                        text = pageDetails?.data?.name ?: "null",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        pageDetails?.data?.category_name ?: "Default",
                                        color = GreyTxt,
                                        fontSize = 14.sp
                                    )

                                        Text(
                                            "${pageDetails?.data?.followers_count} followers",
                                            color = GreyTxt,
                                            fontSize = 14.sp
                                        )

                                }
                            }
                            Text(
                                text = pageDetails?.data?.bio ?: "null bio",
                                color = GreyTxt,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = {
                                        val id = pageId
                                        if (id != null) {
                                            viewModel.followPage(id)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Blue
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        painter = if(pageDetails?.data?.is_following == 0)
                                            painterResource(R.drawable.heart_svgrepo_com)
                                        else
                                            painterResource((R.drawable.heart_filled_svgrepo_com)),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        if(pageDetails?.data?.is_following == 0)"  Like" else " Liked")
                                }
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = LLBlue
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.share_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Blue
                                    )
                                    Text(
                                        " Share",
                                        color = Blue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        items(pagePosts?.posts ?: emptyList()) { post ->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Post(
                    uName = post.username,
                    caption = post.caption,
                    createdAt = formatPostTime(post.created_at),
                    mediaList = post.media
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PageScreenPreview(){
    PageScreen()
}
