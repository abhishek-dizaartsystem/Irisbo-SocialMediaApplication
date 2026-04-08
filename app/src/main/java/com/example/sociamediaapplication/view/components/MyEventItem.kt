package com.example.sociamediaapplication.view.components

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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.TransparentWhite
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun MyEventItem(
    date: String = "Feb 15, 2026",
    name: String = "Tech Conference 2026",
    time: String = "9:00 A.M",
    location: String = "Convention Center",
    interestedPeople: Int = 405,
    isInterested: Boolean = false,
    onEventClick: () -> Unit = {},
    image: String? = null,
    onDelete: () -> Unit = {}
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
            Box {
                AsyncImage(
                    model = image ?: R.drawable.conference,
                    contentDescription = "",
                    modifier = Modifier.aspectRatio(2.2f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
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

                // Dropdown menu in top-right corner
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "More options",
                            tint = Black,
                            modifier = Modifier.size(24.dp).rotate(90f)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                expanded = false
                                onDelete()
                            }
                        )
                    }
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