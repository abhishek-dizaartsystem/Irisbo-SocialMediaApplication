package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.formatToTime
import com.example.sociamediaapplication.model.ChatMessage
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DGrey


@Composable
fun ChatBubble(
    message: ChatMessage,
    isRead: Boolean = false,       // 👈 add this
    onDeleteClick: () -> Unit = {},
    attachments: List<String> = emptyList()
) {

    var showMenu by remember { mutableStateOf(false) }

    Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp),
    horizontalArrangement = if (message.isUser)
        Arrangement.End else Arrangement.Start
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .widthIn(min = 60.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            showMenu = true
                        }
                    )
                }
        ){
            if(attachments.isNotEmpty()){
                attachments.forEach { attachment->
                    AsyncImage(
                        model = attachment,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp).aspectRatio(1f).clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

            }
            Box(
                modifier = Modifier
                    .background(
                        color = if (message.isUser)
                            Color(0xFFE0E0E0)   // user bubble
                        else
                            Color(0xFFB3E5FC),  // friend bubble
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(14.dp)
                    .widthIn(min = 60.dp, max = 260.dp)
            ) {
                Text(
                    text = message.message,
                    color = Black,
                    fontSize = 14.sp
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 1.dp, end = 10.dp)
            ) {
                Text(
                    text = formatToTime(message.msgTime),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(end = 2.dp)
                )
                if(message.isUser){
                    Icon(
                        painter = painterResource(R.drawable.double_check_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = if (isRead) Blue else DGrey
                    )
                }

            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        showMenu = false
                        onDeleteClick()
                    }
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ChatBubblePreview(){
    ChatBubble(
        ChatMessage("Hello Kartik", true, "12:44"),
    )
}