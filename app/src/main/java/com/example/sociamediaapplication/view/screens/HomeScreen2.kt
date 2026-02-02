package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.Post

@Composable
fun HomeScreen2(modifier: Modifier){
    LazyColumn(
        modifier = modifier
    ) {
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .width(150.dp)
                            .height(220.dp)
                            .padding(horizontal = 4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()){
                            Image(
                                painter = painterResource(R.drawable.rectangle_5),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(300.dp)
                            )
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    modifier = Modifier.background(
                                        color = Blue,
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.add_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.height(40.dp),
                                        tint = White
                                    )
                                }
                                Text(
                                    text = "Create Story",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
                items(4) {
                    Column(
                        modifier = Modifier
                            .width(150.dp)
                            .height(220.dp)
                            .padding(horizontal = 4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(R.drawable.rectangle_6),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(300.dp)
                            )
                        }
                    }
                }
            }
        }
        items(count = 4) {
            Post(caption = "Springs bright, all sustainable! Everything shown was made before 1982, except vintage")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen2Preview(){
    HomeScreen2(modifier = Modifier.fillMaxSize())
}
