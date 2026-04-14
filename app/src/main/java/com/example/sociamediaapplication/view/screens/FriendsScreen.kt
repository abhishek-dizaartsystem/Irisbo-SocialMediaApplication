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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.FriendRequestItem
import com.example.sociamediaapplication.view.components.FriendsItem
import com.example.sociamediaapplication.view.components.FriendsSuggestionItem
import com.example.sociamediaapplication.viewmodel.FriendViewModel

@Composable
fun FriendsScreen(
    navController: NavController = rememberNavController(),
    viewModel: FriendViewModel = viewModel()
){
    val suggestedUsers by viewModel.suggestedUsers.collectAsState()
    val receivedRequests by viewModel.receivedRequests.collectAsState()
    val sentRequests by viewModel.sentRequests.collectAsState()
    val myFriends by viewModel.myFriends.collectAsState()

    var searchTxt by remember { mutableStateOf("") }

    var optionSelected by remember { mutableStateOf("Requests") }

    var optionSelected2 by remember { mutableStateOf("sent") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
            ) {

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = "Friends",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Create",
                        fontSize = 18.sp,
                        color = Transparent
                    )






                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = searchTxt,
                        onValueChange = {newMessage->
                            searchTxt = newMessage
                        },
                        placeholder = {
                            Text(
                                text = "Search friends",
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
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.search_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                tint = GreyTxt
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Spacer(
                    modifier = Modifier
                        .background(color = Grey)
                        .height(1.dp)
                        .fillMaxWidth()
                )

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = BackgroundColor
                )
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                optionSelected = "All Friends"
                            },
                            modifier = Modifier.fillMaxWidth(0.33f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "All Friends",
                                fontSize = 16.sp,
                                color = if (optionSelected == "All Friends") Black else GreyTxt
                            )


                        }
                        Button(
                            onClick = {
                                optionSelected = "Requests"
                            },
                            modifier = Modifier.fillMaxWidth(0.5f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Requests",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Requests") Black else GreyTxt
                            )


                        }
                        Button(
                            onClick = {
                                optionSelected = "Suggestions"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Suggestions",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Suggestions") Black else GreyTxt
                            )


                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.33f)
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "All Friends") Blue else Transparent
                                )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "Requests") Blue else Transparent
                                )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "Suggestions") Blue else Transparent
                                )
                        )
                    }

                }
                if(optionSelected=="All Friends"){
//                    FriendsItem()
                    items(myFriends?.data ?: emptyList()){item->
                        FriendsItem(
                            name = item.username,
//                            mutualsCount = item.mutuals_count        //Not Present,
                            profileImage = item.profile_image,
                            onUnfriend = {
                                viewModel.unfriendUser(item.id)
                            }
                        )
                    }
                }else if(optionSelected=="Requests"){
                    item {
                        Row() {
                            Button(
                                onClick = {
                                    optionSelected2 = "sent"
                                }
                            ) {
                                Text("Sent")
                            }
                            Button(
                                onClick = {
                                    optionSelected2 = "received"
                                }
                            ) {
                                Text("Received")
                            }
                        }
                    }
                    if(optionSelected2 == "sent"){
                        items(sentRequests?.data ?: emptyList()){item->
                            FriendRequestItem(
                                name = item.username,
//                                    mutualsCount = item.mutuals_count        //Not Present,
                                onCancelRequest = {
                                    viewModel.cancelRequest(item.id)
                                },
                                isSentType = true,
                                profileImage = item.profile_image
                            )
                        }
                    }else{
                        items(receivedRequests?.data ?: emptyList()){item->
                            FriendRequestItem(
                                name = item.username,
//                                mutualsCount = item.mutuals_count        //Not Present,
                                profileImage = item.profile_image,
                                onAccept = {
                                    viewModel.acceptRequest(item.id)
                                },
                                onReject = {
                                    viewModel.rejectRequest(item.id)
                                }
                            )
                        }
                    }

                }else{
                    items(suggestedUsers?.data ?: emptyList()){user->
                        FriendsSuggestionItem(
                            name = user.username,
                            mutualsCount = user.mutual_friends_count,
                            profileImage = user.profile_image,
                            sendFriendRequest = {
                                viewModel.sendFriendRequest(user.id)
                            },
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FriendsScreenPreview(){
    FriendsScreen()
}