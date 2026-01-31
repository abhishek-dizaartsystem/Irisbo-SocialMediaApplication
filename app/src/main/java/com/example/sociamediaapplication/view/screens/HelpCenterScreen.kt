package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DGrey
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun HelpCenterScreen(){

    val faqList = listOf(
        "How do I reset my password?" to
                "Go to Settings > Password & Security and enter your current password followed by your new password. Click Save to update.",

        "How do I make my account private?" to
                "Go to Settings > Privacy and enable Private Account.",

        "How do I delete my account?" to
                "Navigate to Settings > Account > Delete Account and follow the instructions.",

        "Why can't I see someone's posts?" to
                "They may have a private account or you might be blocked.",

        "How do I report inappropriate content?" to
                "Tap the three dots on the post and select Report."
    )

    var expandedIndex by remember { mutableStateOf(0) }

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


                    Text(
                        text = "Save...",
                        fontSize = 14.sp,
                        color = White
                    )

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
            ){
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Frequently Asked Questions",
                        color = GreyTxt,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                item {
                    Card(
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ){
                        Column() {
                            faqList.forEachIndexed { index, faq->
                                val isExpanded = expandedIndex == index

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expandedIndex =
                                                if (isExpanded) -1 else index
                                        }
                                        .padding(16.dp)
                                ){
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Text(
                                            text = faq.first,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 15.sp
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.back_svgrepo_com),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(14.dp)
                                                .rotate(
                                                    if(isExpanded) 90f else -90f
                                                ),
                                            tint = GreyTxt
                                        )
                                    }
                                    if (isExpanded) {
                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(
                                            text = faq.second,
                                            color = GreyTxt,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                                if (index != faqList.lastIndex) {
                                    Spacer(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .fillMaxWidth()
                                            .background(color = Grey)
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Contact Us",
                        color = GreyTxt,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }

                item{
                    Card(
                        onClick = {},
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(12.dp)
                        ) {
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { },
                                            modifier = Modifier.size(38.dp),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = LLBlue
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.chat_dots_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier.size(20.dp),
                                                tint = Blue
                                            )

                                        }
                                        Column(
                                            modifier = Modifier.padding(start = 16.dp)
                                        ) {
                                            Text(
                                                text = "Chat with Support",
                                                color = Black,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = "Get instant help",
                                                color = GreyTxt
                                            )
                                        }

                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(14.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { },
                                            modifier = Modifier.size(38.dp),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = LLBlue
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.page_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier.size(28.dp),
                                                tint = Blue
                                            )

                                        }
                                        Column(
                                            modifier = Modifier.padding(start = 16.dp)
                                        ) {
                                            Text(
                                                text = "Terms of Service",
                                                color = Black,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = "Read our terms",
                                                color = GreyTxt
                                            )
                                        }
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(14.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Grey)
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { },
                                            modifier = Modifier.size(38.dp),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = LLBlue
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.email_svgrepo_com),
                                                contentDescription = "",
                                                modifier = Modifier.size(26.dp),
                                                tint = Blue
                                            )

                                        }
                                        Column(
                                            modifier = Modifier.padding(start = 16.dp)
                                        ) {
                                            Text(
                                                text = "Email Support",
                                                color = Black,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = "support@irisbo.com",
                                                color = GreyTxt
                                            )
                                        }
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.back_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(14.dp)
                                            .rotate(180f),
                                        tint = DGrey
                                    )
                                }


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HelpCenterScreenPreview(){
    HelpCenterScreen()
}