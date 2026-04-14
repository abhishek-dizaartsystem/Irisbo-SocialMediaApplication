package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LRed
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun FriendRequestItem(
    painter: Painter = painterResource(R.drawable.rectangle_36__2_),
    name: String = "Arjun",
    mutualsCount: String = "4 mutuals",
    profileImage: String? = null,
    onAccept: () -> Unit = {},
    onReject: () -> Unit = {},
    isSentType: Boolean = false,
    onCancelRequest: () -> Unit = {}
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
                    AsyncImage(
                        model = if(profileImage == null) R.drawable.profile_image_placeholder else correctUrl(profileImage),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
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
            if(isSentType){
                Button(
                    onClick = onCancelRequest,
                    colors = ButtonDefaults.buttonColors(LRed)
                ) {
                    Text("Cancel Request", color = Red)
                }
            }else{
                Row() {
                    IconButton(
                        onClick = onAccept,
                        modifier = Modifier
                            .size(44.dp)
                            .padding(end = 8.dp)
                            .aspectRatio(1f),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Blue
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(){
                            Icon(
                                painter = painterResource(R.drawable.profile_1335_svgrepo_com),
                                contentDescription = "",
                                tint = White,
                                modifier = Modifier
                                    .size(14.dp)
                            )
                            Column(
                                modifier = Modifier.padding(start = 12.dp, top = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.tick_svgrepo_com),
                                    contentDescription = "",
                                    tint = White,
                                    modifier = Modifier.size(10.dp)
                                )
                            }

                        }

                    }
                    IconButton(
                        onClick = onReject,
                        modifier = Modifier
                            .size(44.dp)
                            .padding(end = 8.dp)
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .aspectRatio(1f),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = LGrey
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(){
                            Icon(
                                painter = painterResource(R.drawable.profile_1335_svgrepo_com),
                                contentDescription = "",
                                tint = Black,
                                modifier = Modifier
                                    .size(14.dp)
                            )
                            Column(
                                modifier = Modifier.padding(start = 12.dp, top = 2.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.cross_svgrepo_com),
                                    contentDescription = "",
                                    tint = Black,
                                    modifier = Modifier.size(10.dp)
                                )
                            }

                        }

                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendRequestItemPreview(){
    FriendRequestItem()
}