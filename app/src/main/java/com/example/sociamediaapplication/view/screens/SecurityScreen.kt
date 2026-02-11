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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun SecurityScreen(
    navController: NavController = rememberNavController()
){

    var currPass by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

    var isTwoFactor by remember { mutableStateOf(false) }
    var isLoginAlerts by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(White)
            ) {

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White),
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
                        text = "Password and Security",
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
    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Change Password",
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
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Current Password",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            TextField(
                                value = currPass,
                                onValueChange = {newTxt->
                                    currPass = newTxt
                                },
                                placeholder = {
                                    Text("Enter current password")
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Transparent,
                                    unfocusedIndicatorColor = Transparent,
                                    disabledIndicatorColor = Transparent,
                                    unfocusedContainerColor = Transparent,
                                    focusedContainerColor = Transparent
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Grey,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .height(50.dp),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {}
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.chatreadhidden_svgrepo_com),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Enter new password",
                                modifier = Modifier.padding(bottom = 8.dp),
                                fontSize = 16.sp
                            )
                            TextField(
                                value = newPass,
                                onValueChange = {newTxt->
                                    newPass = newTxt
                                },
                                placeholder = {
                                    Text("Enter new password")
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Transparent,
                                    unfocusedIndicatorColor = Transparent,
                                    disabledIndicatorColor = Transparent,
                                    unfocusedContainerColor = Transparent,
                                    focusedContainerColor = Transparent
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Grey,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .height(50.dp),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {}
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.chatreadhidden_svgrepo_com),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Confirm password",
                                modifier = Modifier.padding(bottom = 8.dp),
                                fontSize = 16.sp
                            )
                            TextField(
                                value = confirmPass,
                                onValueChange = {newTxt->
                                    confirmPass = newTxt
                                },
                                placeholder = {
                                    Text("Confirm password")
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Transparent,
                                    unfocusedIndicatorColor = Transparent,
                                    disabledIndicatorColor = Transparent,
                                    unfocusedContainerColor = Transparent,
                                    focusedContainerColor = Transparent
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Grey,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .height(50.dp),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {}
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.chatreadhidden_svgrepo_com),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Security",
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
                                        painter = painterResource(R.drawable.mobile_svgrepo_com),
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
                                        text = "Two-Factor Authentication",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Add extra security",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = isTwoFactor,
                                onCheckedChange = {
                                    isTwoFactor = it
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
                                        painter = painterResource(R.drawable.security_2_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(30.dp),
                                        tint = Blue
                                    )

                                }
                                Column(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "Login Alerts",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Get notified of new logins",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = isLoginAlerts,
                                onCheckedChange = {
                                    isLoginAlerts = it
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
fun SecurityScreenPreview(){
    SecurityScreen()
}