package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.CartItem
import com.example.sociamediaapplication.model.WishlistItem
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.view.components.WishlistItem
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

@Composable
fun WishlistScreen(
    navController: NavController = rememberNavController(),
    viewModel: MarketplaceViewModel = viewModel()
) {

    val wishlistItems by viewModel.wishListItems.collectAsState()

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
                        text = "WishList",
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
                                modifier = Modifier.size(30.dp).rotate(90f)
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
            if(wishlistItems != emptyList<WishlistItem>()){
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(wishlistItems){item->
                        WishlistItem(
                            productId = item.productId,
                            productImage = item.productImage,
                            productName = item.productName,
                            sellerName = item.sellerName,
                            price = item.price,
                            onAddToCart = {
                                viewModel.addToCart(
                                    CartItem(
                                        productId = item.productId,
                                        productName = item.productName,
                                        painter = item.productImage,
                                        price = item.price,
                                        productCount = 1
                                    )
                                )
                            },
                            onDelete = {
                                viewModel.removeFromWishlist(productId = item.productId)
                            }
                        )
                    }
                }
            }
            else{
                Text(
                    "Wishlist is empty"
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WishlistScreenPreview(){
    WishlistScreen()
}