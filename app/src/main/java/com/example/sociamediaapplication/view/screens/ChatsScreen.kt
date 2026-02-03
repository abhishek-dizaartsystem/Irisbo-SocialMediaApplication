package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.ChatThumbnail

@Composable
fun ChatsScreen(){

    var chatListSeen = remember { List(8){R.drawable.rectangle_5} }

    var chatListUnseen = remember { List(3){R.drawable.rectangle_58} }

    var searchTxt by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                BackgroundColor
            )
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = searchTxt,
            onValueChange = {newMessage->
                searchTxt = newMessage
            },
            placeholder = {
                Text("Search User, Posts...")
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent,
                unfocusedContainerColor = Transparent,
                focusedContainerColor = Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 3.dp,
                    color = Blue,
                    shape = RoundedCornerShape(16.dp)
                ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn() {
            items(chatListUnseen){friendPic->
                ChatThumbnail(
                    friendName = "Nikhil Rawat",
                    recentMsg = "Hello",
                    msgTime = "4:32A.M",
                    friendProfilePhoto = painterResource(friendPic),
                    seen = false
                )
            }
            items(chatListSeen){friendPic->
                ChatThumbnail(
                    friendName = "Kartik",
                    recentMsg = "Sent a photo",
                    msgTime = "2 days ago",
                    friendProfilePhoto = painterResource(friendPic)
                )
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatsScreenPreview(){
    ChatsScreen()
}