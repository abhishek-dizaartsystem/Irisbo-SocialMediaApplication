package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.view.components.CartItem
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

@Composable
fun CartScreen(
    navController: NavController = rememberNavController(),
    onCheckout: ()-> Unit = {},
    viewModel: MarketplaceViewModel = viewModel()
) {

    val cartItems by viewModel.cartItems.collectAsState()

    val cartSum by viewModel.cartSum.collectAsState()


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
                Spacer(
                    modifier = Modifier
                        .background(color = Grey)
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }

        },
        containerColor = BackgroundColor
    ){innerPadding->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if(cartItems.isNotEmpty()){
                        LazyColumn(
                            modifier = Modifier.padding(16.dp).fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            items(cartItems){item->
                                CartItem(
                                    productId = item.id,
                                    productImage = item.product_image.firstOrNull(),
                                    productName = item.name,
                                    sellerName = item.username?:"Seller",
                                    price = item.price.toFloatOrNull()?:0f,
                                    quantity = item.quantity,
                                    onIncreaseQuantity = {
                                        viewModel.increaseProductQuantity(
                                            item.id,
                                            onError = {}
                                        )
                                    },
                                    onDecreaseQuantity = {
                                        viewModel.decreaseProductQuantity(
                                            item.id,
                                            onError = {}
                                        )
                                    },
                                    onDelete = {
                                        viewModel.deleteCartProduct(item.id, onError = {})
                                    }
                                )
                            }



                        }
                    }
                    else{
                        Text(
                            text = "Cart is Empty",
                            fontSize = 20.sp
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(BackgroundColor)
                ) {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal",
                            color = GreyTxt,
                            fontSize = 16.sp
                        )
                        Text(
                            text = String.format("%.2f", cartSum),
                            color = GreyTxt,
                            fontSize = 16.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Shipping",
                            color = GreyTxt,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Free",
                            color = GreyTxt,
                            fontSize = 16.sp
                        )
                    }
                    HorizontalDivider()
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            color = Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = String.format("%.2f", cartSum),
                            color = Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {
                            onCheckout()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Proceed to checkout",

                        )
                    }

                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview(){
    CartScreen()
}