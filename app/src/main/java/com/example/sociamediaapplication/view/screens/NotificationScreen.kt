package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.sociamediaapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.view.components.NotificationItem

@Composable
fun NotificationScreen(){
    var notifications = remember { List(4){R.drawable.rectangle_24} }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn() {
            item {
                Text(
                    text = "Recent",
                    fontSize = 18.sp
                )
            }

            items(notifications){ notificationImg->
                NotificationItem(
                    painter = painterResource(notificationImg),
                    notificationSince = "Just Now"
                )
            }
            item {
                Text(
                    text = "Past Week",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreenPreview(){
    NotificationScreen()
}