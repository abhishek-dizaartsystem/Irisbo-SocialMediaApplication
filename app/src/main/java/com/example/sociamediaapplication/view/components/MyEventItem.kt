package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.TransparentWhite
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun MyEventItem(
    date: String = "Feb 15, 2026",
    name: String = "Tech Conference 2026",
    time: String = "9:00 A.M",
    location: String = "Convention Center",
    interestedPeople: Int = 405,
    isInterested: Boolean = true,
    onEventClick: () -> Unit = {},
    image: String? = null
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.padding(vertical = 8.dp)
            .clickable{
                onEventClick()
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    model = image ?: R.drawable.conference,
                    contentDescription = "",
                    modifier = Modifier.aspectRatio(2.2f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(
                            color = TransparentWhite,
                            shape = RoundedCornerShape(50.dp)
                        )
                ) {
                    Text(
                        text = date,
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp,
                                horizontal = 8.dp
                            )
                    )
                }
            }

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.clock_three_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        tint = GreyTxt
                    )
                    Text(
                        text = time,
                        color = GreyTxt,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.location_pin_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        tint = GreyTxt
                    )
                    Text(
                        text = location,
                        color = GreyTxt,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.team_3),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        tint = GreyTxt
                    )
                    Text(
                        text = "$interestedPeople going",
                        color = GreyTxt,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
                Row() {
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        Text(
                            text = "Update Event",
                            color = White,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        Text(
                            text = "View participants",
                            color = White,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyEventItemPreview(){
    MyEventItem()
}