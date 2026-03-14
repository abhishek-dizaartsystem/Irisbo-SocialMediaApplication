package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.ChatThumbnail

@Composable
fun ChatsScreen(
    onChatClick: (String) -> Unit
){

    var chatListSeen = remember { List(8){R.drawable.rectangle_5} }

    var chatListUnseen = remember { List(3){R.drawable.rectangle_58} }

    var searchTxt by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .background(
                BackgroundColor
            )
    ) {
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
                .padding(horizontal = 12.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    tint = GreyTxt
                )
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn() {
            items(chatListUnseen){friendPic->
                ChatThumbnail(
                    onChatClick = { userId ->
                        onChatClick(userId)
                    },
                    userId = "123",
                    friendName = "Nikhil Rawat",
                    recentMsg = "Hello",
                    msgTime = "4:32A.M",
                    friendProfilePhoto = painterResource(friendPic),
                    seen = false
                )
                HorizontalDivider()
            }
            items(chatListSeen){friendPic->
                ChatThumbnail(
                    onChatClick,
                    userId = "12",
                    friendName = "Kartik",
                    recentMsg = "Sent a photo",
                    msgTime = "2 days ago",
                    friendProfilePhoto = painterResource(friendPic)
                )
                HorizontalDivider()
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatsScreenPreview(){
    ChatsScreen(
        onChatClick = {}
    )
}