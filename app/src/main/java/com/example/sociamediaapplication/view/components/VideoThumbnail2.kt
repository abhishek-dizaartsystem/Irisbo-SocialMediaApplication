package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun VideoThumbnail2(
    text: String = "How to open Restaurant Startup",
    channelName: String = "Guru ji",
    views: String = "20K views",
    uploadTime: String = "3 w ago",
    durationTime: String = "18:43",
    painter: Painter = painterResource(R.drawable.rectangle_36),
    imageUrl: String? = null,
    onVideoClick: ()->Unit = {}
){
    Column(
        modifier = Modifier.fillMaxWidth().clickable{
            onVideoClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ){
            AsyncImage(
                model = correctUrl(imageUrl ?: ""),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .padding(bottom = 4.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .background(
                        color = Transparent,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = durationTime,
                    color = White,
                    modifier = Modifier
                        .background(
                            color = Black,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(2.dp)
                )
            }

        }

        Text(
            text = text,
            modifier = Modifier.padding(vertical = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$channelName, $uploadTime",
            color = GreyTxt,
            fontSize = 10.sp
            )
    }
}
