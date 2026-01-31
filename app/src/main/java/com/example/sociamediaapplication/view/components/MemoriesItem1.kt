package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun MemoriesItem1(
    date: String = "12/08/2022",
    caption: String = "Amazing sunset at the mountains 🏔️",
    painter: Painter = painterResource(R.drawable.scene1)
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column() {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
            ) {
                Text(
                    text = date,
                    color = Blue,
                    fontSize = 14.sp
                )
                Text(
                    text = "You shared this memory",
                    color = GreyTxt,
                    fontSize = 12.sp
                )
            }
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.aspectRatio(1.7f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = caption,
                    color = Black,
                    fontSize = 14.sp,
                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LLBlue
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.share_svgrepo_com),
                        contentDescription = "",
                        tint = Blue,
                        modifier = Modifier.size(20.dp  )
                    )
                    Text(
                        "Share",
                        color = Blue,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoriesItem1Preview(){
    MemoriesItem1()
}