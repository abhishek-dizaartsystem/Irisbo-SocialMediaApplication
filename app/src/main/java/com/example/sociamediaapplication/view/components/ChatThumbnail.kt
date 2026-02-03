package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun ChatThumbnail(
    onChatClick: (String)-> Unit,
    userId:String,
    friendName: String,
    recentMsg: String,
    msgTime: String,
    friendProfilePhoto: Painter,
    seen: Boolean = true
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (seen) White else LBlue
            )
            .padding(horizontal = 12.dp)
            .clickable(
                onClick = { onChatClick(userId) }
            )
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier.size(50.dp)
            ) {
                Image(
                    painter = friendProfilePhoto,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = friendName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                    Text(
                        text = recentMsg,
                        fontSize = 16.sp,
                        color = Black,
                        fontWeight = if(seen) FontWeight.Medium else FontWeight.SemiBold
                    )
                }
                Text(
                    text = msgTime,
                    fontSize = 14.sp,
                    color = GreyTxt
                )

            }

        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun ChatTHumbnailPreview(){
    ChatThumbnail(
        onChatClick = {},
        friendName = "Kartik",
        recentMsg = "Hello",
        msgTime = "fri at 5:40A.M",
        friendProfilePhoto = painterResource(R.drawable.rectangle_24),
        seen = false,
        userId = "1"
    )
}