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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.SavedCollectionCard

@Composable
fun SavedScreen(){

    var searchTxt by remember { mutableStateOf("") }

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
                        .background(White),
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
                        text = "Saved",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Save",
                        fontSize = 18.sp,
                        color = White
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

    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    color = White
                )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item(span = { GridItemSpan(2) }){
                    Text(
                        text = "Collections",
                        color = GreyTxt,
                        modifier = Modifier.padding(bottom = 4.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
                items(4){
                    SavedCollectionCard()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SavedScreenPreview(){
    SavedScreen()
}