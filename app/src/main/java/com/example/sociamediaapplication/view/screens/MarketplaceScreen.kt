package com.example.sociamediaapplication.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.CategoriesMarketplace
import com.example.sociamediaapplication.model.MarketplaceItem
import com.example.sociamediaapplication.model.WishlistItem
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.MarketPlaceItem
import com.example.sociamediaapplication.view.components.VendorDashboardCard
import com.example.sociamediaapplication.view.components.VendorProductCard
import com.example.sociamediaapplication.view.navigation.MarketRoutes
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

@Composable
fun MarketplaceScreen(
    bNavController: NavController = rememberNavController(),
    navController: NavController = rememberNavController(),
    onProductClick: (String)-> Unit = {},
    viewModel: MarketplaceViewModel = viewModel()
){

    var searchTxt by remember { mutableStateOf("") }

    var isVendor by remember { mutableStateOf(true) }

    var isAnalyticsSelected by remember { mutableStateOf(false) }

    val vendorProducts by viewModel.vendorProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }
    var productList = remember {
        mutableStateListOf(
            MarketplaceItem(
                "1",
                R.drawable.gaming_chair,
                199f,
                "Gaming Chair"
            ),
            MarketplaceItem(
                "2",
                R.drawable.iphone,
                899f,
                "Iphone"
            ),
            MarketplaceItem(
                "3",
                R.drawable.sofa,
                499f,
                "Modern Sofa set"
            ),
            MarketplaceItem(
                "4",
                R.drawable.shoe,
                299f,
                "Nike Jordan"
            ),
            MarketplaceItem(
                "5",
                R.drawable.dslr_camera,
                199f,
                "$350"
            ),
            MarketplaceItem(
                "6",
                R.drawable.gaming_chair,
                699f,
                "Gaming Chair"
            )
        )
    }

    var categories = remember { listOf(
        CategoriesMarketplace((R.drawable.car_svgrepo_com), "Vehicles"),
        CategoriesMarketplace((R.drawable.house_svgrepo_com), "Property"),
        CategoriesMarketplace((R.drawable.laptop_svgrepo_com), "Electronics"),
        CategoriesMarketplace((R.drawable.cloth_sweater_tshirt_svgrepo_com), "Clothing"),
        CategoriesMarketplace((R.drawable.sofa_svgrepo_com), "Furniture"),
        CategoriesMarketplace((R.drawable.basketball_svgrepo_com), "Sports"),
    ) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
            ) {

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundColor),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            bNavController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = "MarketPlace",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Row(
                        Modifier.padding(end = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),

                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate(MarketRoutes.Cart.route)
                            },
                            Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.cart_shopping_svgrepo_com),
                                contentDescription = null,
                                Modifier.size(24.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                navController.navigate(MarketRoutes.Wishlist.route)
                            },
                            Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.heart_filled_svgrepo_com),
                                contentDescription = null,
                                Modifier.size(24.dp)
                            )
                        }
                    }



                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = searchTxt,
                        onValueChange = {newMessage->
                            searchTxt = newMessage
                        },
                        placeholder = {
                            Text(
                                text = "Search Marketplace",
                                color = GreyTxt
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = LGrey,
                            focusedContainerColor = LGrey
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .height(54.dp)
                            .fillMaxWidth(0.85f),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.search_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                tint = GreyTxt
                            )
                        }
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(50.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = LGrey
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sort_filter_filtering_filters_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(90f)

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
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundColor)
        ) {
            LazyVerticalGrid (
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item(span = { GridItemSpan(2) }){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isVendor)"Dashboard" else "Categories",
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = if (isVendor)"Vendor" else "User",
                                modifier = Modifier.padding(horizontal = 4.dp),
                                fontSize = 14.sp
                            )
                            Switch(
                                checked = isVendor,
                                onCheckedChange = {
                                    isVendor = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )

                        }

                    }

                }

                if(isVendor){
                    item {
                        VendorDashboardCard()
                    }
                    item {
                        VendorDashboardCard()
                    }
                    item {
                        VendorDashboardCard()
                    }
                    item {
                        VendorDashboardCard()
                    }
                    item(span = { GridItemSpan(2) }){

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
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if(isAnalyticsSelected) LGrey else White
                                    ),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = "Products",
                                        color = if(isAnalyticsSelected) GreyTxt else Black,
                                        fontSize = 16.sp
                                    )
                                }
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if(!isAnalyticsSelected) LGrey else White
                                    ),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = "Analytics",
                                        color = if(!isAnalyticsSelected) GreyTxt else Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }


                    }
                    if(isAnalyticsSelected){

                    }else{
                        item(span = { GridItemSpan(2) }){
                            Button(
                                onClick = {
                                    navController.navigate(MarketRoutes.AddProduct.route)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Blue
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.add_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = White
                                )
                                Text(
                                    text = "Add Product",
                                    color = White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        items(
                            vendorProducts,
                            span = { GridItemSpan(2) }
                        ){ product ->

                            VendorProductCard(
                                productName = product.name,
                                price = "₹${product.price}",
                                stock = product.stock,
                                sold = 0,
                                revenue = "₹${product.price.toDoubleOrNull()?.times(product.stock) ?: 0}",
                                isActive = true,
                                imageUrl = product.product_image?.let {
                                    "${RetrofitClient.BASE_URL}uploads/$it"
                                } ?: "https://picsum.photos/200"
                            )
                        }
                    }

                }
                else{
                    item(span = { GridItemSpan(2) }) {

                        LazyRow() {
                            items(categories){category->
                                Card(
                                    onClick = {},
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = painterResource(category.painter),
                                            contentDescription = "",
                                            modifier = Modifier.size(30.dp)
                                        )
                                        Text(
                                            text = category.text
                                        )
                                    }

                                }
                                Spacer(Modifier.width(12.dp))
                            }
                        }
                    }
                    item(span = {GridItemSpan(2)}) {
                        Text(
                            text = "Today's Picks",
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    items(vendorProducts){product->

                        val url = product.product_image?.let {
                            "${RetrofitClient.BASE_URL}uploads/$it"
                        }
                        Log.d("IMG_DEBUG", "URL = $url")

                        MarketPlaceItem(
                            productId = product.id.toString(),
                            imageUrl = product.product_image?.let {
                                "${RetrofitClient.BASE_URL}uploads/$it"
                            },
                            productName = product.name,
                            price = product.price.toFloatOrNull()?:0f,
                            onClick = onProductClick,
                            onIconClick = {
                                viewModel.addToWishlist(
                                    product.id,
                                    onError = {error->
                                        Log.e("WISHLIST_DEBUG", error)
                                    }
                                )
                            }
                        )
                    }
                }




            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MarketplaceScreenPreview(){
    MarketplaceScreen()
}