package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun FriendsItem(
    painter: Painter = painterResource(R.drawable.rectangle_36__2_),
    name: String = "Kartik",
    mutualsCount: String = "4 mutuals",
    isOnline: Boolean = true,
    profileImage: String? = null,
    onUnfriend: () -> Unit = {},
    onOtherProfileClick: () -> Unit = {}
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
                            .clip(HexagonShape)
                            .clickable{
                                onOtherProfileClick()
                            },
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
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clickable{
                                    onOtherProfileClick()
                                }
                        )
                    }
                    Text(
                        text = mutualsCount,
                        fontSize = 14.sp,
                        color = GreyTxt
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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

                Button(
                    onClick = onUnfriend,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.width(70.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(Blue)
                ) {
                    Text("Unfriend")
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsItemPreview(){
    FriendsItem()
}