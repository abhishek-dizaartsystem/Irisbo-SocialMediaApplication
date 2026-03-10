package com.example.sociamediaapplication.view.screens

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
import android.util.Log
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
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.formatPostTime
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.PagePost
import com.example.sociamediaapplication.viewmodel.PageViewModel

@Composable
fun PageScreen(
    painter: Painter = painterResource(R.drawable.react_laptop),
    pageId: String = "1",
    navController: NavController = rememberNavController(),
    viewModel: PageViewModel = viewModel()
){

    val pagePosts by viewModel.pagePosts.collectAsState()

    LaunchedEffect(pageId) {
        Log.d("PAGE_SCREEN", "LaunchedEffect triggered, pageId='$pageId'")
        val id = pageId.toIntOrNull()
        if (id != null) {
            Log.d("PAGE_SCREEN", "Calling loadPagePosts with id=$id")
            viewModel.loadPagePosts(id)
        } else {
            Log.e("PAGE_SCREEN", "pageId is not a valid Int: '$pageId'")
        }
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
                                        text = "React Developers($pageId)",
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
                                    onClick = {
                                        val id = pageId.toIntOrNull()
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
                                        painter = painterResource(R.drawable.heart_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text("  Like")
                                }
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = LLBlue
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        "Follow",
                                        color = Blue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        items(pagePosts?.data ?: emptyList()) { post ->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                PagePost(
                    uName = post.username,
                    caption = post.caption,
                    sincePosted = formatPostTime(post.created_at),
                    mediaList = null
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
