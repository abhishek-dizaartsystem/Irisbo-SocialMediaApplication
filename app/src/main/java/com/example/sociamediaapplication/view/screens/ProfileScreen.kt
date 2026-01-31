package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DBlue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun ProfileScreen(){

    var postSelected by remember { mutableStateOf(true) }
    val userPosts = remember { List(15) { R.drawable.rectangle_24 } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = White
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(modifier = Modifier.height(220.dp)) {
                Image(
                    painter = painterResource(R.drawable.rectangle_24),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row() {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ){
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(134.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = White
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
                                containerColor = GreyBtn
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.camera_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }

                }

                Row() {
                    Column() {
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(40.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = GreyBtn
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.camera_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Text(
            text = "John Doe",
            fontSize = 25.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
        Text(
            text = "Bio",
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(0.37f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Add Status",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                border = BorderStroke(1.dp, Grey)
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit_svgrepo_com__1_),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = Black
                )
                Text(
                    text = "Edit Profile",
                    fontSize = 16.sp,
                    color = Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                border = BorderStroke(1.dp, Grey)
            ) {
                Icon(
                    painter = painterResource(R.drawable.menu_navigation_grid_1528_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp),
                    tint = Black
                )
                Text(
                    text = "Menu",
                    fontSize = 16.sp,
                    color = Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xfff8fafc)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(postSelected) LBlue else White
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Posts",
                    color = if(postSelected) DBlue else Black
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(!postSelected) LBlue else White
                ),
                contentPadding = PaddingValues(0.dp)

            ) {
                Text(
                    text = "Reels",
                    color = if(!postSelected) LBlue else GreyTxt
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = Grey)
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "Details",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.briefcase_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = GreyTxt
                )
                Text(
                    text = "Works at Dizaart",
                    modifier = Modifier
                        .padding(start = 6.dp),
                    color = GreyTxt
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.scholar_cap),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = GreyTxt
                )
                Text(
                    text = "Studied at AKTU",
                    modifier = Modifier
                        .padding(start = 6.dp),
                    color = GreyTxt
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.location_pin_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = GreyTxt
                )
                Text(
                    text = "Lives in India",
                    modifier = Modifier
                        .padding(start = 6.dp),
                    color = GreyTxt
                )
            }
        }
        //Posts or Reels
        if (postSelected) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 Columns
                modifier = Modifier
                    .fillMaxSize()
                    .background(GreyTxt),
                contentPadding = PaddingValues(1.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(userPosts) { postImage ->
                    Image(
                        painter = painterResource(id = postImage),
                        contentDescription = "Post",
                        modifier = Modifier
                            .aspectRatio(1f) // Makes it a perfect square
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        } else {
            // Placeholder for Reels
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Reels content goes here")
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}