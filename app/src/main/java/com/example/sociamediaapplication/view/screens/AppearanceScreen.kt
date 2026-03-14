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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun AppearanceScreen(
    navController: NavController = rememberNavController()
) {

    val radioOptions = listOf("Light", "Dark", "System")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(BackgroundColor)
            ) {

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = ""
                        )
                    }

                    Text(
                        text = "Appearance",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
                        modifier = Modifier.height(36.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 14.sp,
                            color = White
                        )
                    }
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
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(BackgroundColor)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Theme",
                        color = GreyTxt,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }

                item {
                    Card(
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {

                        Column {

                            // Light Option
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.size(42.dp),
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = LLBlue
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.sun_svgrepo_com),
                                            contentDescription = "",
                                            tint = Blue
                                        )
                                    }

                                    Column(
                                        modifier = Modifier.padding(start = 12.dp)
                                    ) {
                                        Text(
                                            text = "Light",
                                            fontSize = 16.sp,
                                            color = Black
                                        )
                                        Text(
                                            text = "Always use light mode",
                                            color = GreyTxt
                                        )
                                    }
                                }

                                RadioButton(
                                    selected = selectedOption == "Light",
                                    onClick = {
                                        onOptionSelected("Light")
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Blue
                                    )
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(Grey)
                            )

                            // Dark Option
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.size(42.dp),
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = LLBlue
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.night_svgrepo_com),
                                            contentDescription = "",
                                            tint = Blue
                                        )
                                    }

                                    Column(
                                        modifier = Modifier.padding(start = 12.dp)
                                    ) {
                                        Text(
                                            text = "Dark",
                                            fontSize = 16.sp,
                                            color = Black
                                        )
                                        Text(
                                            text = "Always use dark mode",
                                            color = GreyTxt
                                        )
                                    }
                                }

                                RadioButton(
                                    selected = selectedOption == "Dark",
                                    onClick = {
                                        onOptionSelected("Dark")
                                    }
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(Grey)
                            )

                            // System Option
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.size(42.dp),
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = LLBlue
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.pc_monitor_screen_svgrepo_com),
                                            contentDescription = "",
                                            tint = Blue
                                        )
                                    }

                                    Column(
                                        modifier = Modifier.padding(start = 12.dp)
                                    ) {
                                        Text(
                                            text = "System",
                                            fontSize = 16.sp,
                                            color = Black
                                        )
                                        Text(
                                            text = "Match device settings",
                                            color = GreyTxt
                                        )
                                    }
                                }

                                RadioButton(
                                    selected = selectedOption == "System",
                                    onClick = {
                                        onOptionSelected("System")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppearanceScreenPreview(){
    AppearanceScreen()
}