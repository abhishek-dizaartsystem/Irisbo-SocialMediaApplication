package com.example.sociamediaapplication.view.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun VideoFrame(){

    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black)
    ) {
        Image(
            painter = painterResource(R.drawable.rectangle_58),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp))
                .aspectRatio(1.8f)
        )
        Column(
            modifier = Modifier
                .padding(vertical = 2.dp)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.8f),
                contentAlignment = Alignment.BottomStart
            ){
                Column(
                    modifier = Modifier
                        .background(DTransparentBlack)
                ) {
                    CustomProgressBar(
                    value = 1f,
                    maxValue = 2f
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = if(isPlaying) R.drawable.pause_svgrepo_com else R.drawable.play_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Grey
                                )
                            }
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.skip_forward_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Grey
                                )
                            }

                            Text(
                                text = "10:00 / 20:00",
                                modifier = Modifier.padding(start = 8.dp),
                                color = Grey
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.closed_caption_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Grey
                                )
                            }
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.settings_logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Grey
                                )
                            }
                        }
                    }

                }

            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun VideoFramePreview(){
    VideoFrame()
}