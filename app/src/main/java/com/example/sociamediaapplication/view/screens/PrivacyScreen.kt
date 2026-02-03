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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(){

    var isPvtAccount by remember { mutableStateOf(true) }
    var showOnline by remember { mutableStateOf(false) }
    var showLocation by remember { mutableStateOf(true) }
    var allowMessages by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
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
                                onClick = {},
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back_svgrepo_com),
                                    contentDescription = ""
                                )
                            }
                            Text(
                                text = "Privacy",
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

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor
                ),
                expandedHeight = 40.dp
            )

        }
    ){innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(BackgroundColor)
        ) {
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Account Privacy",
                        color = GreyTxt,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                item{
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
                                        painter = painterResource(R.drawable.team_3),
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
                                        text = "Private Account",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Only followers can see post",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = isPvtAccount,
                                onCheckedChange = {
                                    isPvtAccount = it
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
                                        painter = painterResource(R.drawable.eye_svgrepo_com),
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
                                        text = "Show Activity Status",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Let others see when you're online",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = showOnline,
                                onCheckedChange = {
                                    showOnline = it
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
                                        painter = painterResource(R.drawable.location_pin_svgrepo_com),
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
                                        text = "Show Location",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Display location on posts",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = showLocation,
                                onCheckedChange = {
                                    showLocation = it
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
                        text = "Messages",
                        color = GreyTxt,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                item{
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
                                        painter = painterResource(R.drawable.chat_dots_svgrepo_com),
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
                                        text = "Allow Messages",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Anyone can send you messages",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = allowMessages,
                                onCheckedChange = {
                                    allowMessages = it
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
fun PrivacyScreenPreview(){
    PrivacyScreen()
}