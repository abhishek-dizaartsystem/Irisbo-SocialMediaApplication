package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.view.components.CategoryCard
import com.example.sociamediaapplication.view.components.CustomSearchBar


@Composable
fun CategoryScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        CustomSearchBar(
            onClick = {}
        )
        LazyRow(
            modifier = Modifier.padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(24.dp, 124.dp)
                ) {
                    Text(
                        text = "Very Short",
                        modifier = Modifier.padding(horizontal = 14.dp),
                        fontSize = 16.sp
                    )
                }
            }
            item {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(24.dp, 124.dp)
                ) {
                    Text(
                        text = "Short",
                        modifier = Modifier.padding(horizontal = 14.dp),
                        fontSize = 16.sp
                    )
                }
            }
            item {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(54.dp, 104.dp)
                ) {
                    Text(
                        text = "Medium",
                        modifier = Modifier.padding(horizontal = 14.dp),
                        fontSize = 16.sp
                    )
                }
            }

            item {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(54.dp, 104.dp)
                ) {
                    Text(
                        text = "Long",
                        modifier = Modifier.padding(horizontal = 14.dp),
                        fontSize = 16.sp
                    )
                }
            }

            item {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .widthIn(54.dp, 104.dp)
                ) {
                    Text(
                        text = "Very Long",
                        modifier = Modifier.padding(horizontal = 14.dp),
                        fontSize = 16.sp
                    )
                }
            }


        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(14) {
                CategoryCard()
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoryScreenPreview(){
    CategoryScreen()
}