package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.CategoriesMarketplace
import com.example.sociamediaapplication.model.MarketplaceItem
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.MarketPlaceItem

@Composable
fun MarketplaceScreen(
    navController: NavController = rememberNavController()
){

    var searchTxt by remember { mutableStateOf("") }

    var productList = remember {
        mutableStateListOf(
            MarketplaceItem(
                R.drawable.gaming_chair,
                "$199",
                "Gaming Chair"
            ),
            MarketplaceItem(
                R.drawable.iphone,
                "$899",
                "Iphone"
            ),
            MarketplaceItem(
                R.drawable.sofa,
                "$499",
                "Modern Sofa set"
            ),
            MarketplaceItem(
                R.drawable.shoe,
                "$199",
                "Nike Jordan"
            ),
            MarketplaceItem(
                R.drawable.dslr_camera,
                "$199",
                "$350"
            ),
            MarketplaceItem(
                R.drawable.gaming_chair,
                "$199",
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
                        text = "MarketPlace",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Save",
                        fontSize = 18.sp,
                        color = White
                    )


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
                    Text(
                        text = "Categories",
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
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

                items(productList){product->
                    MarketPlaceItem(
                        painter = painterResource(product.painter),
                        productName = product.productName,
                        price = product.price
                    )
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