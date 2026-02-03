package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.ChatMessage
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.DGrey
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.ChatBubble


val messages = listOf(
    ChatMessage("Hye Abhishek this side", true),
    ChatMessage("Hello I am Kartik", false),
    ChatMessage("Send me the required documents", true),
    ChatMessage("Okay I'll provide u asap", false),
    ChatMessage("Thanks", true)
)



@Composable
fun ChatScreen(
    userId: String = "Kartik",
    navController: NavController
){

    var typeMessage by remember { mutableStateOf("") }
    var showAttachmentMenu by remember { mutableStateOf(true) }


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LGrey),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Transparent),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.widthIn(30.dp, 70.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = Black
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.dot_small_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = userId,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row() {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.call_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp).rotate(-135f)
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.video_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp).rotate(90f)
                        )
                    }
                }


            }
        },
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(color = LGrey)
                    .padding(all = 4.dp)
                    .fillMaxWidth()
            ) {
                Row() {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sticker_add_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.attachment_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                TextField(
                    value = typeMessage,
                    onValueChange = {newMessage->
                        typeMessage = newMessage
                    },
                    placeholder = {
                        Text("Type")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                        disabledIndicatorColor = Transparent
                    ),
                    shape = RoundedCornerShape(50.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = { showAttachmentMenu = true},
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
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = showAttachmentMenu,
                    onDismissRequest = { showAttachmentMenu = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("Camera") },
                        onClick = {
                            showAttachmentMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.camera_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Photo") },
                        onClick = {
                            showAttachmentMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.photo_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Video") },
                        onClick = {
                            showAttachmentMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.video_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Document") },
                        onClick = {
                            showAttachmentMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.page_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )
                }


            }
        }
    ) {innerPadding->
        LazyColumn(
            modifier = Modifier
                .background(LBlue)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(messages){msg->
                ChatBubble(msg)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview(){
    ChatScreen(
        navController = rememberNavController()
    )
}