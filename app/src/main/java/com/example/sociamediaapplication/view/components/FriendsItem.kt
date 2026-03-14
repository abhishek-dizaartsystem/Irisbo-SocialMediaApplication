package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun FriendsItem(
    painter: Painter = painterResource(R.drawable.rectangle_36__2_),
    name: String = "Kartik",
    mutualsCount: String = "4 mutuals",
    isOnline: Boolean = true
){
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd
                ){
                    Image(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    if(isOnline){
                        Column(
                            modifier = Modifier.padding(2.dp)
                        ) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(12.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = White
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.dot),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(10.dp),
                                    tint = Green
                                )
                            }

                        }

                    }
                }

                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Text(
                        text = mutualsCount,
                        fontSize = 14.sp,
                        color = GreyTxt
                    )
                }
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(30.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.chat_dots_svgrepo_com),
                    contentDescription = "",
                    tint = GreyTxt
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsItemPreview(){
    FriendsItem()
}