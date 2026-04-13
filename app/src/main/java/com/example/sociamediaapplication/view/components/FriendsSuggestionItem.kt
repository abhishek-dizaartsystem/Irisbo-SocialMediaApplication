package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun FriendsSuggestionItem(
    painter: Painter = painterResource(R.drawable.rectangle_36__2_),
    name: String = "Aakash",
    mutualsCount: Int = 4,
    profileImage: String? = null,
    sendFriendRequest: () -> Unit = {}
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
                        text = "$mutualsCount mutuals",
                        fontSize = 14.sp,
                        color = GreyTxt
                    )
                }
            }
            Row() {
                IconButton(
                    onClick = sendFriendRequest,
                    modifier = Modifier
                        .width(80.dp)
                        .padding(end = 8.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Blue
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row() {
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
                                    painter = painterResource(R.drawable.add_svgrepo_com),
                                    contentDescription = "",
                                    tint = White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }

                        }
                        Text(
                            text = "Add",
                            color = White,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }



                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsSuggestionItemPreview(){
    FriendsSuggestionItem()
}