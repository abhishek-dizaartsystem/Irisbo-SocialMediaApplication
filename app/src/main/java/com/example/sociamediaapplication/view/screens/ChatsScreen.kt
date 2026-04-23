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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.formatPostTime
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.ChatThumbnail
import com.example.sociamediaapplication.viewmodel.ChatViewModel

@Composable
fun ChatsScreen(
    onChatClick: (Int) -> Unit,
    chatViewModel: ChatViewModel = viewModel()
){

    var chatListSeen = remember { List(8){R.drawable.rectangle_5} }

    var chatListUnseen = remember { List(3){R.drawable.rectangle_58} }

    var searchTxt by remember { mutableStateOf("") }

    val chats by chatViewModel.conversations.collectAsState()

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

            items(chats?.conversations ?: emptyList()){chat->
                ChatThumbnail(
                    onChatClick = {
                        onChatClick(chat.conversation_id)
                    },
                    userId = chat.other_user_id,
                    friendName = chat.other_user_name,
                    recentMsg = chat.content ?: "null",
                    msgTime = formatPostTime(chat.last_message_at?:""),
                    friendProfilePhoto = chat.other_user_profile_image,
                    seen = chat.last_message_id == chat.last_read_message_id
                )
                HorizontalDivider()
            }
//            items(chatListUnseen){friendPic->
//                ChatThumbnail(
//                    onChatClick = {
//                        onChatClick(123)
//                    },
//                    userId = 123,
//                    friendName = "Nikhil Rawat",
//                    recentMsg = "Hello",
//                    msgTime = "4:32A.M",
//                    friendProfilePhoto = "https://picsum.photos/200/300",
//                    seen = false
//                )
//                HorizontalDivider()
//            }
//            items(chatListSeen){friendPic->
//                ChatThumbnail(
//                    onChatClick = { onChatClick(12) },
//                    userId = 12,
//                    friendName = "Kartik",
//                    recentMsg = "Sent a photo",
//                    msgTime = "2 days ago",
//                    friendProfilePhoto = "https://picsum.photos/200/300"
//                )
//                HorizontalDivider()
//            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatsScreenPreview(){
    ChatsScreen(
        onChatClick = {},
    )
}