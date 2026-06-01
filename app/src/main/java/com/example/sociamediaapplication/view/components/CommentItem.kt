package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun CommentItem(
    userName: String = "@coolboy",
    comment: String = "This is Amazing",
    profileImage: String = "",
    commentTime: String = "",
    totalLikes: Int = 20,
    totalDislikes: Int = 2,
    totalReplies: Int = 3,
    isLiked: Boolean = false,
    isDisliked: Boolean = false,
    onLiked: ()-> Unit = {},
    onDisliked: ()-> Unit = {},
    onReplyClicked: ()-> Unit = {},
    onUserProfileClick: ()-> Unit = {}
){
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        IconButton(
            onClick = {
                onUserProfileClick()
            },
            modifier = Modifier.size(48.dp)
                .border(1.dp, Black, HexagonShape),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Transparent
            ),
            shape = HexagonShape
        ) {
            AsyncImage(
                model = correctUrl(profileImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
        }
        Column(
            modifier = Modifier.padding(start = 12.dp, top = 2.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = userName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = commentTime,
                    fontSize = 12.sp,
                    color = GreyTxt,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = comment,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            Row() {
                Button(
                    onClick = {
                        onLiked()
                    },
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(50.dp, 150.dp)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                ) {
                    Icon(
                        painter = painterResource(
                            if(isLiked) R.drawable.like_svgrepo_com__1_ else R.drawable.like_svgrepo_com
                        ),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = GreyTxt
                    )
                    Text(
                        text = totalLikes.toString(),
                        fontSize = 14.sp,
                        color = GreyTxt,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Button(
                    onClick = {
                        onDisliked()
                    },
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(50.dp, 150.dp)
                        .padding(end = 12.dp),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                ) {
                    Icon(
                        painter = painterResource(
                            if(isDisliked) R.drawable.like_svgrepo_com__1_ else R.drawable.like_svgrepo_com
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(180f),
                        tint = GreyTxt
                    )
                    Text(
                        text = totalDislikes.toString(),
                        fontSize = 14.sp,
                        color = GreyTxt,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Button(
                onClick = {
                    onReplyClicked()
                },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
            ) {
                Text(
                    text = "${totalReplies} replies",
                    modifier = Modifier.padding(end = 8.dp),
                    color = Blue
                )
                Icon(
                    painter = painterResource(R.drawable.back_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier
                        .size(14.dp)
                        .rotate(-90f),
                    tint = Blue
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentItemPreview(){
    CommentItem(
        userName = "@coolboy",
        comment = "This is Amazing",
        totalLikes = 10,
        totalDislikes = 2,
        totalReplies = 4
    )
}