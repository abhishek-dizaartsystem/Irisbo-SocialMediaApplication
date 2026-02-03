package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DBlue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.UploadItem
import com.example.sociamediaapplication.view.components.VideoThumbnail

@Composable
fun UploadScreen(){

    var caption by remember { mutableStateOf("") }

    val uploadList = remember {
        mutableStateListOf(
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                color = BackgroundColor
            )
    ) {
        Column(
            modifier = Modifier.height(220.dp)
        ) {
            TextField(
                value = caption,
                onValueChange = {newValue->
                    caption = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Transparent,
                    unfocusedContainerColor = Transparent,
                    focusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent

                ),
                modifier = Modifier.fillMaxWidth()
                    .heightIn(50.dp, 200.dp),
                label = {
                    Text(
                        text = "What's on your mind?",
                        color = GreyTxt
                    )
                }
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Grey)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LLBlue)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.photo_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(26.dp),
                        tint = DBlue
                    )
                    Text(
                        text = "Photo",
                        fontSize = 16.sp,
                        color = DBlue,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LLBlue)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.video_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(26.dp),
                        tint = DBlue
                    )
                    Text(
                        text = "Video",
                        fontSize = 16.sp,
                        color = DBlue,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(end = 8.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Text(
                    text = "Post",
                    fontSize = 16.sp,
                    color = White,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }


        }
        Spacer(Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(uploadList) { thumbnailImage ->
                UploadItem(
                    painter = painterResource(thumbnailImage),
                    onDelete = {
                        uploadList.remove(thumbnailImage)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UploadScreenPreview(){
    UploadScreen()
}