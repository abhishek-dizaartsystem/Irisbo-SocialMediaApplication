package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGreen
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLGreen
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.EarningsItem
import com.example.sociamediaapplication.view.components.PaymentsItem
import com.example.sociamediaapplication.viewmodel.MonetizationViewModel

@Composable
fun MonetizationScreen(
    navController: NavController = rememberNavController(),
    monetizationViewModel: MonetizationViewModel = viewModel()
){

//    var isMonetizationEnabled by remember { mutableStateOf(false) }

    var earningsSelected by remember { mutableStateOf(true) }

    val walletSummary by monetizationViewModel.walletSummary.collectAsState()
    val orderwiseEarnings by monetizationViewModel.orderwiseEarnings.collectAsState()
    val payoutHistory by monetizationViewModel.payoutHistory.collectAsState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = "Video Monetization ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Row(modifier = Modifier.width(16.dp)) {
                    }




                }

                Spacer(
                    modifier = Modifier
                        .background(color = Grey)
                        .height(1.dp)
                        .fillMaxWidth()
                )

            }
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    color = BackgroundColor
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                item{
//                    Card(
//                        modifier = Modifier
//                            .padding(vertical = 8.dp)
//                            .fillMaxWidth(),
//                        colors = CardDefaults.cardColors(
//                            containerColor = if(isMonetizationEnabled) LLGreen else White
//                        ),
//                        elevation = CardDefaults.cardElevation(2.dp),
//                        border = BorderStroke(
//                            width = 1.dp,
//                            color = if(isMonetizationEnabled) LGreen else Grey
//                        )
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .padding(12.dp)
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ){
//                            Row() {
//                                if(isMonetizationEnabled){
//                                    Box(
//                                        contentAlignment = Alignment.Center
//                                    ){
//                                        Icon(
//                                            painter = painterResource(R.drawable.circle_svgrepo_com),
//                                            contentDescription = "",
//                                            modifier = Modifier.size(40.dp),
//                                            tint = LGreen
//                                        )
//                                        Icon(
//                                            painter = painterResource(R.drawable.tick_svgrepo_com),
//                                            contentDescription = "",
//                                            modifier = Modifier.size(24.dp),
//                                            tint = LGreen
//                                        )
//                                    }
//                                }
//
//                                Column(
//                                    modifier = Modifier
//                                        .padding(start = 12.dp)
//                                        .fillMaxWidth(0.7f)
//                                ) {
//                                    Text(
//                                        text = "Content Monetization",
//                                        fontSize = 18.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                    Text(
//                                        text = "You're ${if(!isMonetizationEnabled) "not" else ""} earning from your content",
//                                        color = GreyTxt
//                                    )
//                                }
//                            }
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                Switch(
//                                    checked = isMonetizationEnabled,
//                                    onCheckedChange = {
//                                        isMonetizationEnabled = it
//                                    },
//                                    colors = SwitchDefaults.colors(
//                                        checkedThumbColor = White,
//                                        checkedTrackColor = LGreen,
//                                        uncheckedThumbColor = White,
//                                        uncheckedTrackColor = Grey,
//                                        uncheckedBorderColor = Grey,
//                                    )
//                                )
//                                Row(
//                                    modifier = Modifier
//                                        .background(
//                                            color = if(isMonetizationEnabled)LGreen else Grey,
//                                            shape = RoundedCornerShape(16.dp)
//                                        )
//                                ) {
//                                    Text(
//                                        text = if(isMonetizationEnabled) "Enabled" else "Disabled",
//                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
//                                        color = Black
//                                    )
//                                }
//                            }
//
//
//                        }
//                    }
//                }

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween,
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Wallet",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Icon(
                                    painter = painterResource(
                                        R.drawable.wallet_svgrepo_com
                                    ),
                                    contentDescription = null,
                                    tint = LGreen,
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Text(
                                text = "₹${walletSummary?.data?.total_balance ?: 0}",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Total Wallet Balance",
                                color = GreyTxt
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Column {

                                    Text(
                                        text = "Refunded Balance",
                                        color = GreyTxt
                                    )

                                    Text(
                                        text = "₹${walletSummary?.data?.total_refunded ?: 0}",
                                        fontWeight = FontWeight.SemiBold,
                                        color = LGreen
                                    )
                                }

                                Column(
                                    horizontalAlignment =
                                        Alignment.End
                                ) {

                                    Text(
                                        text = "Pending Payout",
                                        color = GreyTxt
                                    )

                                    Text(
                                        text = "₹${walletSummary?.data?.pending_balance ?: 0}",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Button(
                                onClick = { },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LGreen
                                )
                            ) {
                                Text(
                                    text = "Withdraw Funds",
                                    color = Black
                                )
                            }
                        }
                    }
                }
                item{
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = White
                            ),
                            elevation = CardDefaults.cardElevation(2.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .aspectRatio(1.6f)
                                    .fillMaxSize()
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.rupee_sign_svgrepo_com),
                                        contentDescription = null,
                                        tint = LGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Available Balance",
                                        color = GreyTxt,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                                Text(
                                    text = "₹${walletSummary?.data?.available_balance ?: 0}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = White
                            ),
                            elevation = CardDefaults.cardElevation(2.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .aspectRatio(1.6f)
                                    .fillMaxSize(),
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.rupee_sign_svgrepo_com),
                                        contentDescription = null,
                                        tint = LGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Total Earnings",
                                        color = GreyTxt,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                                Text(
                                    text = "₹${walletSummary?.data?.total_earned ?: 0}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                Text(
                                    text = "All time",
                                    color = GreyTxt
                                )
                            }
                        }
                    }
                }
                item{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = LGrey,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Button(
                                onClick = {
                                    earningsSelected = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(!earningsSelected) LGrey else White
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(0.5f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Earnings",
                                    color = if(!earningsSelected) GreyTxt else Black,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    earningsSelected = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(earningsSelected) LGrey else White
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Payments",
                                    color = if(earningsSelected) GreyTxt else Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                if(earningsSelected){
                    item {
                        Text(
                            text = "Top earning videos",
                            fontWeight = FontWeight.Bold,
                            color = GreyTxt,
                            fontSize = 16.sp
                        )
                    }
                    items(
                        orderwiseEarnings?.data?.earnings ?: emptyList()
                    ){ earning ->

                        EarningsItem(
                            earning = earning
                        )
                    }
                }else{
                    item {
                        Button(
                            onClick = {},
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxSize(),
                            border = BorderStroke(
                                width = 1.dp,
                                color = Grey,
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.card_credit_finance_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.size(24.dp),
                                        tint = Black
                                    )
                                    Text(
                                        text = "Payment Method",
                                        color = Black,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                                Icon(
                                    painter = painterResource(R.drawable.back_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .rotate(180f),
                                    tint = Black
                                )
                            }
                        }
                    }
                    items(
                        payoutHistory?.data?.payouts ?: emptyList()
                    ){ payout ->

                        PaymentsItem(
                            payout = payout
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MonetizationScreenPreview(){
    MonetizationScreen()
}