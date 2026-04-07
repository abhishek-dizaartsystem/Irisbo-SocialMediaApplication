package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun ReelDetailComponent(
    profile_image_url: String? = null,
    caption: String? = null,
    username: String? = null
){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = if(profile_image_url==null) R.drawable.rectangle_24 else correctUrl(profile_image_url),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .clip(HexagonShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = username ?: "Creative Studios",
                    fontSize = 20.sp,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = username ?: "@creative",
                    fontSize = 16.sp,
                    color = LGrey
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        Text(
            text = caption ?: "Behind the scenes of our latest photoshoot!",
            fontSize = 16.sp,
            color = White,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReelDetailComponentPreview(){
    ReelDetailComponent()
}