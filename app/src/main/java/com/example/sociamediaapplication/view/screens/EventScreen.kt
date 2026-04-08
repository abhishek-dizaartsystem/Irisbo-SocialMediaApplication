package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.correctUrl2
import com.example.sociamediaapplication.data.utils.formatToDate
import com.example.sociamediaapplication.data.utils.formatToTime
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.viewmodel.EventViewModel

@Composable
fun EventScreen(
    onBack: () -> Unit = {},
    navController: NavController = rememberNavController(),
    viewModel: EventViewModel,
    isCreator: Boolean = false
) {

    val eventDetails by viewModel.eventDetails.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        /* ---------------- HEADER IMAGE ---------------- */

        item {
            Box {

                AsyncImage(
                    model = correctUrl2(eventDetails?.event?.cover_image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(White.copy(.9f), CircleShape)
                            .clickable { navController.popBackStack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Row {

//                        Box(
//                            modifier = Modifier
//                                .size(42.dp)
//                                .background(White.copy(.9f), CircleShape)
//                                .clickable {},
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.save_icon),
//                                contentDescription = null,
//                                modifier = Modifier.size(20.dp)
//                            )
//                        }
//
//                        Spacer(Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(White.copy(.9f), CircleShape)
                                .clickable {},
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.share_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .background(Color(0xff1d6ef2), RoundedCornerShape(50))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(eventDetails?.event?.category ?: "Default", color = White)
                }
            }
        }

        /* ---------------- CONTENT ---------------- */

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundColor)
                    .padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        eventDetails?.event?.title?:"Tech Conference",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        "Free",
                        color = Color(0xff1d6ef2),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(16.dp))

                /* DATE ROW */

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xffeef2f7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.calendar_svgrepo_com),
                            contentDescription = null,
                            tint = Color(0xff5c6b80),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text(formatToDate(eventDetails?.event?.start_time?: "Feb 15, 2025"))
                        Text(
                            "${formatToTime(eventDetails?.event?.start_time?: "9:00 AM")} - ${formatToTime(eventDetails?.event?.end_time?: "5:00 PM")}",
                            color = Color.Gray
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                /* LOCATION ROW */

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xffeef2f7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.location_pin_svgrepo_com),
                            contentDescription = null,
                            tint = Color(0xff5c6b80),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Text(eventDetails?.event?.location_name?:"Convention Center, Downtown")
                }

                Spacer(Modifier.height(18.dp))
                HorizontalDivider(color = Color(0xffe7ecf3))
                Spacer(Modifier.height(18.dp))

                /* ORGANIZER ROW */

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(Color(0xffeef2f7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("T", fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text("TechHub Community")
                        Text("Organizer", color = Color.Gray)
                    }
                }

                Spacer(Modifier.height(18.dp))
                HorizontalDivider(color = Color(0xffe7ecf3))
                Spacer(Modifier.height(18.dp))

                /* ATTENDING */

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Attending",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text("${eventDetails?.event?.participants ?: 0} going", color = Color.Gray)
                }

                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Row {
                        repeat(3) {
                            Image(
                                painter = painterResource(R.drawable.rectangle_5),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.White, CircleShape)
                            )
                        }
                    }

                    Spacer(Modifier.width(8.dp))
                    Text("+${eventDetails?.event?.participants ?: 0} others", color = Color.Gray)
                }

                Spacer(Modifier.height(18.dp))
                HorizontalDivider(color = Color(0xffe7ecf3))
                Spacer(Modifier.height(18.dp))

                /* ABOUT */

                Text(
                    "About this event",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    eventDetails?.event?.description?: "Description of event",
                    color = Color(0xff6b7a90)
                )

                Spacer(Modifier.height(24.dp))

                /* BUTTONS */

//                Row(verticalAlignment = Alignment.CenterVertically) {
//
//                    Button(
//                        onClick = {},
//                        modifier = Modifier.weight(1f),
//                        shape = RoundedCornerShape(16.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = LLBlue
//                        )
//                    ) {
//                        Text("Registered", color = Blue)
//                    }
//
//                    Spacer(Modifier.width(12.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .size(40.dp)
//                            .background(Color(0xffeef2f7), RoundedCornerShape(14.dp))
//                            .clickable {},
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.chat_dots_svgrepo_com),
//                            contentDescription = null,
//                            tint = Color(0xff5c6b80),
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//
//                    Spacer(Modifier.width(8.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .size(40.dp)
//                            .background(Color(0xffeef2f7), RoundedCornerShape(14.dp))
//                            .clickable {},
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.heart_svgrepo_com),
//                            contentDescription = null,
//                            tint = Color(0xff5c6b80),
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventScreenPreview(){
    EventScreen(
        viewModel = viewModel()
    )
}
