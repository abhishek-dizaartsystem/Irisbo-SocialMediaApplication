package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.view.components.Post

@Composable
fun HomeScreen1(){
    LazyColumn() {
        items(count = 4) {
            Post(caption = "Springs bright, all sustainable! Everything shown was made before 1982, except vintage")
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen1Preview(){
    HomeScreen1()
}
