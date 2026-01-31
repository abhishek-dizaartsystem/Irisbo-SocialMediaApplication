package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun UserVideoItem(
    text: String = "How to open Restaurant Startup | Vlogs by Guru ji | ft. Arjun Singh",
    uploadTime: String = "3 w ago",
    durationTime: String = "18:43",
    painter: Painter = painterResource(R.drawable.rectangle_36),
    likes:String = "14.3K",
    shares: String = "1.4K",
    views: String = "143.4K"
) {
    Column() {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.weight(0.6f)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.6f)
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
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.heart_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = likes,
                        fontSize = 18.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.eye_outlined_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = views,
                        fontSize = 18.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.share_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = shares,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "$views, $uploadTime",
            color = GreyTxt,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun UserVideoItemPreview(){
    UserVideoItem()
}