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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.DiscoverGroupsItem
import com.example.sociamediaapplication.view.components.DiscoverPagesItem
import com.example.sociamediaapplication.view.components.GroupsItem
import com.example.sociamediaapplication.view.components.PagesItem

@Composable
fun PagesScreen(){

    var searchTxt by remember { mutableStateOf("") }

    var isDiscoverSelected by remember { mutableStateOf(true) }

    var optionSelected by remember { mutableStateOf("Discover") }

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
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = "Pages",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Row() {
                        Button(
                            onClick = {},
                            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
                            modifier = Modifier.height(34.dp).padding(end = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Blue)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.add_svgrepo_com),
                                contentDescription = ""
                            )
                            Text(
                                text = "Create",
                                fontSize = 18.sp
                            )
                        }
                    }




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
                                text = "Search groups",
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
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                optionSelected = "Your Pages"
                            },
                            modifier = Modifier.fillMaxWidth(0.33f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Your Pages",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Your Pages") Black else GreyTxt
                            )


                        }
                        Button(
                            onClick = {
                                optionSelected = "Liked"
                            },
                            modifier = Modifier.fillMaxWidth(0.5f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Liked",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Liked") Black else GreyTxt
                            )


                        }
                        Button(
                            onClick = {
                                optionSelected = "Discover"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Discover",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Discover") Black else GreyTxt
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
                                    color = if (optionSelected == "Your Pages") Blue else Transparent
                                )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "Liked") Blue else Transparent
                                )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "Discover") Blue else Transparent
                                )
                        )
                    }

                }
                items(10){
                    if(optionSelected=="Your Pages"){
                        PagesItem()
                    }else if(optionSelected=="Liked"){
                        DiscoverPagesItem(isLiked = true)
                    }else{
                        DiscoverPagesItem()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PagesScreenPreview(){
    PagesScreen()
}