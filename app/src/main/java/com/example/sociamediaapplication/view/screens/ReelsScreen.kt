package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.Reel
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.ReelDetailComponent
import com.example.sociamediaapplication.view.components.ReelItem

@Composable
fun ReelsScreen(
    loading: Boolean = false,
    reels: List<Reel> = emptyList(),
    startIndex: Int = 0,
    onLike: (Reel) -> Unit = {}
) {

    var isMuted by remember { mutableStateOf(false) }

    if (loading && reels.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", color = White)
        }
        return
    }

    val pagerState = rememberPagerState(initialPage = startIndex, pageCount =  { reels.size })

    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
    ) { page ->
        val reel = reels[page]
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            val isVisible = pagerState.currentPage == page

            ReelItem(
                videoUrl = reel.video_url,
                isVisible = isVisible,
                isMuted = isMuted
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight(0.8f)
                ) {
                    IconButton(
                        onClick = {
                            isMuted = !isMuted
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
                        modifier = Modifier.size(45.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isMuted) R.drawable.sound_svgrepo_com else R.drawable.mute_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = White
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .height(230.dp)
                            .padding(end = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = {
                                onLike(reel)

                            },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
                            modifier = Modifier.size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if(reels[page].is_liked) R.drawable.like_svgrepo_com__1_ else R.drawable.like_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = White
                            )
                        }
                        Text(
                            text = reel.likes_count.toString(),
                            color = White,
                            fontSize = 16.sp
                        )
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
                            modifier = Modifier.size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.comment_outlined_3_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = White
                            )
                        }
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
                            modifier = Modifier.size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.share_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = White
                            )
                        }
//                        IconButton(
//                            onClick = {
//                               // viewModel.toggleSave(page)
//                            },
//                            colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
//                            modifier = Modifier.size(45.dp)
//                        ) {
//                            Icon(
//                                painter = painterResource(
//                                    if(reel.isSaved) R.drawable.save_filled else R.drawable.save_icon),
//                                contentDescription = "",
//                                modifier = Modifier.size(30.dp),
//                                tint = White
//                            )
//                        }
                    }
                }

                Row() {
                    ReelDetailComponent()
                }

            }


        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReelsScreenPreview(){
    ReelsScreen()
}