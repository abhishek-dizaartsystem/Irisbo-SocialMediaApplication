package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JobDashboardCard(
    text: String = "1",
    title: String = "Applicants"
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp))
        Text(title, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun JobDashboardCardScreen(){
    JobDashboardCard()
}