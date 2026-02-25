package com.example.sociamediaapplication.view.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
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
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.AddressStep
import com.example.sociamediaapplication.view.components.PaymentStep
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel
import com.example.sociamediaapplication.viewmodel.PaymentViewModel

enum class CheckOutStep {
    ADDRESS,
    PAYMENT
}

@Composable
fun CheckOutScreen(
    viewModel: MarketplaceViewModel = viewModel(),
    paymentViewModel: PaymentViewModel = viewModel(),
    navController: NavController = rememberNavController()
){

    var currentStep by remember { mutableStateOf(CheckOutStep.ADDRESS) }
    val cartSum by viewModel.cartSum.collectAsState()
    val tax by viewModel.tax.collectAsState()
    val shippingType by viewModel.shippingType.collectAsState()


    Scaffold(
        topBar = {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
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
                        text = "Cart",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        IconButton(
                            onClick = {
                                //navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp)
                                    .rotate(90f)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Blue,
                                CircleShape
                            )
                            .size(30.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(currentStep == CheckOutStep.ADDRESS){
                            Text(
                                text = "1",
                                color = White,
                                fontSize = 16.sp
                            )
                        }
                        else{
                            Icon(
                                painter = painterResource(R.drawable.tick_svgrepo_com),
                                contentDescription = "",
                                tint = White,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                    }
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                            .width(90.dp)
                            .background(
                                color = if (currentStep == CheckOutStep.ADDRESS) Grey else Blue
                            )
                    )
                    Column(
                        modifier = Modifier
                            .background(
                                color = if (currentStep == CheckOutStep.ADDRESS) Grey else Blue,
                                CircleShape
                            )
                            .size(30.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "2",
                            color = if(currentStep == CheckOutStep.ADDRESS) Black else White
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

        },
        bottomBar = {
            Column(
                modifier = Modifier.height(60.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .background(
                            BackgroundColor
                        )
                ) {
                    if(currentStep == CheckOutStep.PAYMENT){
                        Button(
                            onClick = {
                                currentStep = CheckOutStep.ADDRESS
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Grey
                            ),
                            modifier = Modifier
                                .padding(start = 16.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Back",
                                color = Black
                            )
                        }
                    }

                    val activity = LocalActivity.current
                    Button(

                        onClick = {
                            if(activity == null) return@Button

                            if(currentStep == CheckOutStep.ADDRESS){
                                currentStep = CheckOutStep.PAYMENT
                            }else{
                                paymentViewModel.startPayment(
                                    activity = activity,
                                    amount = cartSum,
                                    onError = { msg ->
                                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if(currentStep == CheckOutStep.ADDRESS) "Continue" else "Place Order"
                        )
                    }
                }
                HorizontalDivider(color = Transparent)
            }


        },
        containerColor = BackgroundColor
    ){innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (currentStep) {
                CheckOutStep.ADDRESS -> AddressStep()
                CheckOutStep.PAYMENT -> {
//                    LaunchedEffect(Unit) {
//                        viewModel.loadCheckoutDetails()
//                    }
                    PaymentStep(viewModel)
                }
            }
        }

    }



}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckOutScreenPreview(){
    CheckOutScreen()
}