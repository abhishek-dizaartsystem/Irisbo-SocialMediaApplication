package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun CommentItem(){
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier.size(48.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Red
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle_5),
                contentDescription = "Profile Image",
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
                    text = "@coolboy",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1 week ago",
                    fontSize = 12.sp,
                    color = GreyTxt,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = "Everyone follow this diet surely healthy one",
                modifier = Modifier.padding(vertical = 6.dp)
            )
            Row() {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(50.dp, 150.dp)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.like_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = GreyTxt
                    )
                    Text(
                        text = "89",
                        fontSize = 14.sp,
                        color = GreyTxt,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(50.dp, 150.dp)
                        .padding(end = 12.dp),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.like_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp).rotate(180f),
                        tint = GreyTxt
                    )
                    Text(
                        text = "89",
                        fontSize = 14.sp,
                        color = GreyTxt,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Button(
                onClick = {},
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
            ) {
                Text(
                    text = "7 replies",
                    modifier = Modifier.padding(end = 8.dp),
                    color = Blue
                )
                Icon(
                    painter = painterResource(R.drawable.back_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(14.dp).rotate(-90f),
                    tint = Blue
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentItemPreview(){
    CommentItem()
}