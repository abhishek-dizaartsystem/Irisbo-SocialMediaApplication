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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.ReelDetailComponent
import com.example.sociamediaapplication.view.components.ReelItem

@Composable
fun ReelsScreen() {

    val reels = listOf(
        (R.drawable.rectangle_24),
        (R.drawable.rectangle_6),
        (R.drawable.rectangle_5)
    )

    val pagerState = rememberPagerState(pageCount =  { reels.size })

    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
    ) { page ->
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            ReelItem(
            painter = painterResource(reels[page])
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
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
                        modifier = Modifier.size(45.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.mute_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = White
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .height(220.dp)
                            .padding(end = 6.dp)
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
                            modifier = Modifier.size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.like_svgrepo_com),
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
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(containerColor = DTransparentBlack),
                            modifier = Modifier.size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.save_icon),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = White
                            )
                        }
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