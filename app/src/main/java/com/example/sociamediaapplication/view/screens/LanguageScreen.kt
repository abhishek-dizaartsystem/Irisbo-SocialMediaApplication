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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.LanguageModel
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun LanguageScreen(){

    val languageOptions = listOf<LanguageModel>(
        LanguageModel("English", "English"),
        LanguageModel("Spanish", "Español"),
        LanguageModel("French", "Français"),
        LanguageModel("German", "Deutsch"),
        LanguageModel("Italian", "Italiano"),
        LanguageModel("Portuguese", "Português"),
        LanguageModel("Japanese", "本語"),
        LanguageModel("Korean", "한국어"),
        LanguageModel("Chinese", "中文"),
        LanguageModel("Arabic", "العربية"),
        LanguageModel("Hindi", "हिन्दी")
    )

    val (selectedOption, onOptionsSelected) = remember { mutableStateOf(languageOptions[0].english) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(White)
            ) {

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = ""
                        )
                    }

                    Text(
                        text = "Language",
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
                .background(White)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                item {
                    Card(
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {

                        Column {

                            languageOptions.forEachIndexed { index, language ->

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {

                                    Column {
                                        Text(
                                            text = language.english,
                                            fontSize = 16.sp,
                                            color = Black
                                        )
                                        Text(
                                            text = language.translated,
                                            color = GreyTxt
                                        )
                                    }

                                    if (selectedOption == language.english) {
                                        Icon(
                                            painter = painterResource(R.drawable.tick_svgrepo_com),
                                            contentDescription = "",
                                            tint = Blue,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }

                                if (index != languageOptions.lastIndex) {
                                    Spacer(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .fillMaxWidth()
                                            .background(Grey)
                                    )
                                }
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
fun LanguageScreenPreview(){
    LanguageScreen()
}