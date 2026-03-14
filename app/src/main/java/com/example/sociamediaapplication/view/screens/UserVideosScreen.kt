package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.UserVideoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserVideosScreen(
    navController: NavController = rememberNavController()
){

    val videoList = listOf(
        R.drawable.rectangle_5,
        R.drawable.rectangle_36,
        R.drawable.rectangle_24,
        R.drawable.rectangle_58,
        R.drawable.rectangle_6,
        R.drawable.rectangle_37,
        R.drawable.rectangle_36__2_,
        R.drawable.rectangle_36__1_,
        R.drawable.rectangle_38,
        R.drawable.rectangle_39
    )

    var searchTxt by remember { mutableStateOf("") }

    Scaffold(
        topBar = {


            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundColor),
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
                        text = "Videos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Save",
                        fontSize = 18.sp,
                        color = White
                    )


                }

                Row(
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    TextField(
                        value = searchTxt,
                        onValueChange = {newMessage->
                            searchTxt = newMessage
                        },
                        placeholder = {
                            Text(
                                text = "Search your videos",
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
            }





        },
    ) {innerPadding->
        Column(
            modifier = Modifier
                .background(
                    color = BackgroundColor
                ).fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // IMPORTANT: prevent infinite height
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(videoList) { thumbnailImage ->
                    UserVideoItem(
                        painter = painterResource(thumbnailImage)
                    )
                    HorizontalDivider()
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserVideosScreenPreview(){
    UserVideosScreen()
}