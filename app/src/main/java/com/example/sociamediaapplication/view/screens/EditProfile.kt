package com.example.sociamediaapplication.view.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DBlue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun EditProfileScreen(
    navController: NavController
){
    var name by remember { mutableStateOf("John Doe") }
    var username by remember { mutableStateOf("johndoe") }
    var bio by remember { mutableStateOf("I have my own spaces") }
    var work by remember { mutableStateOf("Dizaart") }
    var education by remember { mutableStateOf("AKTU") }
    var location by remember { mutableStateOf("Ghaziabad") }
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
                        text = "Edit Profile",
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
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(BackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item{
                Box(
                    contentAlignment = Alignment.BottomEnd
                ){
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(138.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = LBlue
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rectangle_5),
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                        )

                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Blue
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.camera_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp),
                            tint = White
                        )
                    }
                }
                Text(
                    text = "Change Profile Photo",
                    modifier = Modifier.padding(vertical = 6.dp),
                    fontSize = 16.sp,
                    color = Blue
                )
            }
            item {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Cover Photo",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.rectangle_24),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                }


            }
            item {
                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Name",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                    TextField(
                        value = name,
                        onValueChange = {newName->
                            name = newName
                        },
                        placeholder = {
                            Text("Enter new Name")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            focusedContainerColor = Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .height(50.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Username",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                    TextField(
                        value = username,
                        onValueChange = {newName->
                            username = newName
                        },
                        placeholder = {
                            Text("Enter new Userame")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            focusedContainerColor = Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .height(50.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Bio",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                    TextField(
                        value = bio,
                        onValueChange = {newBio->
                            bio = newBio
                        },
                        placeholder = {
                            Text("Define yourself...")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            focusedContainerColor = Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .height(50.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Work",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                    TextField(
                        value = work,
                        onValueChange = {newWork->
                            work = newWork
                        },
                        placeholder = {
                            Text("Enter company name")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            focusedContainerColor = Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .height(50.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Education",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                    TextField(
                        value = education,
                        onValueChange = {newEducation->
                            education = newEducation
                        },
                        placeholder = {
                            Text("Enter latest College name")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            focusedContainerColor = Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .height(50.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Name",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 14.sp
                    )
                    TextField(
                        value = location,
                        onValueChange = {newLocation->
                            location = newLocation
                        },
                        placeholder = {
                            Text("Enter your Location")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            focusedContainerColor = Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .height(50.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview(){
    EditProfileScreen(
        navController = rememberNavController()
    )
}