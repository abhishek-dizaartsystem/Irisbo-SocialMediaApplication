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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun NotificationScreen(
    navController: NavController = rememberNavController()
){

    var showPushNotifications by remember { mutableStateOf(false) }
    var showEmailNotifications by remember { mutableStateOf(false) }
    var showLikesNotifications by remember { mutableStateOf(false) }
    var showCommentsNotifications by remember { mutableStateOf(false) }
    var showNewFollowNotifications by remember { mutableStateOf(false) }


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
                            contentDescription = ""
                        )
                    }
                    Text(
                        text = "Notifications",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
                        modifier = Modifier.height(34.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 18.sp
                        )
                    }

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
    ){innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(BackgroundColor)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ){
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "General",
                        color = GreyTxt,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                item {
                    Card(
                        onClick = {},
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(38.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = LLBlue
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.notification_13_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Blue
                                    )

                                }
                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "Push Notifications",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Receive push notifications",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = showPushNotifications,
                                onCheckedChange = {
                                    showPushNotifications = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Grey)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(38.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = LLBlue
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.email_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(26.dp),
                                        tint = Blue
                                    )

                                }
                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "Email Notifications",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Receive email updates",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = showEmailNotifications,
                                onCheckedChange = {
                                    showEmailNotifications = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }

                    }

                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Activity",
                        color = GreyTxt,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                item {
                    Card(
                        onClick = {},
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(38.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = LLBlue
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.heart_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Blue
                                    )

                                }
                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "Likes",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Notify when someone like your post",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = showLikesNotifications,
                                onCheckedChange = {
                                    showLikesNotifications = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Grey)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(38.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = LLBlue
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.comment_3_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(26.dp),
                                        tint = Blue
                                    )

                                }
                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "Comments",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Notify when someone comments on \nyour post",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = showCommentsNotifications,
                                onCheckedChange = {
                                    showCommentsNotifications = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Grey)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(38.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = LLBlue
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.add_friend_24),
                                        contentDescription = "",
                                        modifier = Modifier.size(26.dp),
                                        tint = Blue
                                    )

                                }
                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "New Followers",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Notify when someone follows you",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = showNewFollowNotifications,
                                onCheckedChange = {
                                    showNewFollowNotifications = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }

                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreenPreview(){
    NotificationScreen()
}