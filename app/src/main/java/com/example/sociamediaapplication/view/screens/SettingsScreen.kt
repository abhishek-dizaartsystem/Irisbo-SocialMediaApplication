package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DGrey
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.LRed
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.viewmodel.ProfileViewModel

@Composable
fun SettingsScreen(
    bNavController: NavController = rememberNavController(),
    onEditProfile: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onPrivacy: () -> Unit = {},
    onNotification: () -> Unit = {},
    onAppearance: () -> Unit = {},
    onLanguage: () -> Unit = {},
    onHelpCenter: () -> Unit = {},
    onLogout: () -> Unit = {},
    profileViewModel: ProfileViewModel = viewModel()
){


    val profile by profileViewModel.profile.collectAsState()

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
                        .background(White)
                        .height(36.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            bNavController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = ""
                        )
                    }
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = {}) { }


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
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { },
                                modifier = Modifier.size(68.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = LBlue
                                ),
                                shape = HexagonShape
                            ) {
                                AsyncImage(
                                    model = if(profile?.data?.profile_image == null) R.drawable.profile_image_placeholder else correctUrl(profile?.data?.profile_image),
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(HexagonShape)
                                )

                            }
                            Column(
                                modifier = Modifier.padding(start = 12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = profile?.data?.name ?: "John Doe",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "@${profile?.data?.username ?: "johndoe"}",
                                    fontSize = 15.sp,
                                    color = GreyTxt
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        text = "Account",
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = GreyTxt
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Button(
                                onClick = onEditProfile,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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
                                                painter = painterResource(R.drawable.profile_1341_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier.size(20.dp),
                                                tint = Blue
                                            )

                                        }
                                        Text(
                                            text = "Edit Profile",
                                            modifier = Modifier.padding(start = 16.dp),
                                            color = Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Button(
                                onClick = onSecurity,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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
                                                painter = painterResource(R.drawable.lock_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier.size(22.dp),
                                                tint = Blue
                                            )

                                        }
                                        Text(
                                            text = "Passwords and Security",
                                            modifier = Modifier.padding(start = 16.dp),
                                            color = Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Button(
                                onClick = onPrivacy,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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
                                                modifier = Modifier.size(26.dp),
                                                tint = Blue
                                            )

                                        }
                                        Text(
                                            text = "Privacy",
                                            modifier = Modifier.padding(start = 16.dp),
                                            color = Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        text = "Preferences",
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = GreyTxt
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Button(
                                onClick = onNotification,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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
                                                modifier = Modifier.size(26.dp),
                                                tint = Blue
                                            )

                                        }
                                        Text(
                                            text = "Notifications",
                                            modifier = Modifier.padding(start = 16.dp),
                                            color = Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Button(
                                onClick = onAppearance,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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
                                                painter = painterResource(R.drawable.paint_palette_art_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .rotate(90f),
                                                tint = Blue
                                            )

                                        }
                                        Text(
                                            text = "Appearance",
                                            modifier = Modifier.padding(start = 16.dp),
                                            color = Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Button(
                                onClick = onLanguage,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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
                                                painter = painterResource(R.drawable.global_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier.size(20.dp),
                                                tint = Blue
                                            )

                                        }
                                        Text(
                                            text = "Language",
                                            modifier = Modifier.padding(start = 16.dp),
                                            color = Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        text = "Support",
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = GreyTxt
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Button(
                                onClick = onHelpCenter,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
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
                                                painter = painterResource(R.drawable.help_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier.size(20.dp),
                                                tint = Blue
                                            )

                                        }
                                        Text(
                                            text = "Help Center",
                                            modifier = Modifier.padding(start = 16.dp),
                                            color = Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item{
                    Card(
                        onClick = {},
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = LRed
                        )
                    ) {

                        Button(
                            onClick = {
                                println("Logout Clicked")
                                onLogout()
                            },
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = LRed)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.logout_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .rotate(180f),
                                    tint = Red
                                )


                                Text(
                                    text = "Log Out",
                                    modifier = Modifier.padding(start = 16.dp),
                                    color = Red,
                                    fontSize = 16.sp
                                )
                            }
                            Icon(
                                painter = painterResource(R.drawable.back_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .rotate(180f),
                                tint = DGrey
                            )
                        }




                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        text = "Account",
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = GreyTxt
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview(){
    SettingsScreen()
}