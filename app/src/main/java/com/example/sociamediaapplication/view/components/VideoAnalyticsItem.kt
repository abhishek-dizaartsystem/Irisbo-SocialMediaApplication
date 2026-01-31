package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun VideoAnalyticsItem(
    painter: Painter = painterResource(R.drawable.scene1),
    title: String = "Morning Routine 2024",
    views: String = "11.4K ",
    likes: String = "4.2K",
    uploadDate: String = "Jan 20, 2024"
){
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .aspectRatio(1.5f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            modifier = Modifier.padding( bottom = 6.dp)
                        )
                    }
                    Row() {
                        Icon(
                            painter = painterResource(R.drawable.eye_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(14.dp),
                            tint = GreyTxt
                        )
                        Text(
                            text = views,
                            fontSize = 12.sp,
                            color = GreyTxt,
                            modifier = Modifier
                                .padding(start = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(R.drawable.heart_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(14.dp),
                            tint = GreyTxt
                        )
                        Text(
                            text = likes,
                            fontSize = 12.sp,
                            color = GreyTxt,
                            modifier = Modifier
                                .padding(start = 4.dp)
                        )
                    }


                }
            }

            Text(
                text = uploadDate,
                fontSize = 12.sp,
                color = GreyTxt,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun VideoAnalyticsPreview(){
    VideoAnalyticsItem()
}