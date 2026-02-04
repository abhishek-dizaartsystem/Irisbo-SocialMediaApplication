package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun CommentSection() {

    var searchTxt by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row() {
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
            TextField(
                value = searchTxt,
                onValueChange = {newMessage->
                    searchTxt = newMessage
                },
                placeholder = {
                    Text(
                        text = "Search friends...",
                        color = GreyTxt
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    unfocusedContainerColor = LGrey,
                    focusedContainerColor = LGrey
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )

            IconButton(
                onClick = { },
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.send_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(15f)
                )
            }
        }
        Text(
            text = "Comments",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn() {
            items(10){
                CommentItem(
                    "@coolboy",
                    "This is amazing",
                    20,
                    2,
                    3
                )
                HorizontalDivider()
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CommentSectionPreview(){
    CommentSection()
}