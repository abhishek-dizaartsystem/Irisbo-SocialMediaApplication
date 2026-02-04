package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue

@Composable
fun SendFriendsItem(
    painter: Painter = painterResource(R.drawable.rectangle_36__2_),
    name: String = "Abhinav",
    isSelected: Boolean = true,
    onClick: ()-> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable{onClick()}
        ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            if(isSelected){
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(Blue)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.tick_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(2.dp)
                            .size(10.dp),
                        tint = Black
                    )
                }

            }


        }

        Text(
            text = name,
            modifier = Modifier.padding(top = 4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SendFriendsItemPreview(){
    SendFriendsItem()
}