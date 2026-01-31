package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.BorderStroke
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
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun AdvancedSettingsScreen(){

    var isDataSaver by remember { mutableStateOf(true) }
    var isHDUpload by remember { mutableStateOf(false) }
    var isAutoPlay by remember { mutableStateOf(false) }
    var allowLocation by remember { mutableStateOf(false) }
    var shareUsageAnalytics by remember { mutableStateOf(false) }
    var tryExperimentalFeatures by remember { mutableStateOf(false) }

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
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = "Advanced Settings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Save",
                        fontSize = 18.sp,
                        color = White
                    )


                }

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
                .fillMaxSize()
                .background(
                    color = BackgroundColor
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.database_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Text(
                                    text = "Data and Storage",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                            Text(
                                text = "Manage your data and storage settings",
                                modifier = Modifier.padding(top = 4.dp),
                                color = GreyTxt
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        ) {

                            Column() {
                                Text(
                                    text = "Data Saver",
                                    color = Black,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Reduce data usage",
                                    color = GreyTxt
                                )
                            }

                            Switch(
                                checked = isDataSaver,
                                onCheckedChange = {
                                    isDataSaver = it
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

                            Column() {
                                Text(
                                    text = "HD Upload",
                                    color = Black,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Upload Videos in high quality",
                                    color = GreyTxt
                                )
                            }

                            Switch(
                                checked = isHDUpload,
                                onCheckedChange = {
                                    isHDUpload = it
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

                            Column() {
                                Text(
                                    text = "Auto-play videos",
                                    color = Black,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Play videos automatically",
                                    color = GreyTxt
                                )
                            }

                            Switch(
                                checked = isHDUpload,
                                onCheckedChange = {
                                    isHDUpload = it
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
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.security_2_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Text(
                                    text = "Privacy and Security",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                            Text(
                                text = "Control your privacy settings",
                                modifier = Modifier.padding(top = 4.dp),
                                color = GreyTxt
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        ) {

                            Column() {
                                Text(
                                    text = "Location Services",
                                    color = Black,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Allow Location Access",
                                    color = GreyTxt
                                )
                            }

                            Switch(
                                checked = allowLocation,
                                onCheckedChange = {
                                    allowLocation = it
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

                            Column() {
                                Text(
                                    text = "Usage Analytics",
                                    color = Black,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Share usage data",
                                    color = GreyTxt
                                )
                            }

                            Switch(
                                checked = isHDUpload,
                                onCheckedChange = {
                                    isHDUpload = it
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
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.heartbeat_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Text(
                                    text = "Developer Options",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                            Text(
                                text = "Advanced features for developers",
                                modifier = Modifier.padding(top = 4.dp),
                                color = GreyTxt
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        ) {

                            Column() {
                                Text(
                                    text = "Experimental Features",
                                    color = Black,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Try new features early",
                                    color = GreyTxt
                                )
                            }

                            Switch(
                                checked = tryExperimentalFeatures,
                                onCheckedChange = {
                                    tryExperimentalFeatures = it
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
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.mobile_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Text(
                                    text = "App Management",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LGrey
                                ),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Grey
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.delete_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Black
                                    )
                                    Text(
                                        text = "Clear Cache",
                                        color = Black,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LGrey
                                ),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Grey
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.download_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Black
                                    )
                                    Text(
                                        text = "Export my Data",
                                        color = Black,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LGrey
                                ),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Grey
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.refresh_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Black
                                    )
                                    Text(
                                        text = "Refresh App Data",
                                        color = Black,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        onClick = {},
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Red
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.danger_triangle_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp),
                                    tint = Red
                                )
                                Text(
                                    text = "Danger Zone",
                                    color = Red,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                            Text(
                                text = "Irreversible actions",
                                color = GreyTxt,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Red
                                )
                            ) {
                                Text(
                                    text = "Delete Account",
                                    color = White
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdvancedSettingsScreenPreview(){
    AdvancedSettingsScreen()
}