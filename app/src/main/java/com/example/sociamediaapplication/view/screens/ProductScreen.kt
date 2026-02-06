package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.Specification
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Gold
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGreen
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLGreen
import com.example.sociamediaapplication.ui.theme.TTransparentWhite
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.CustomProgressBar2
import com.example.sociamediaapplication.view.components.ReviewItem

@Composable
fun ProductScreen(
    discount: Float = 18f,
    originalPrice: Float = 120f,
    name: String = "iPhone 14 Pro Max",
    rating: Float = 4.3f,
    reviews: Int = 24
){

    val productImages = listOf(
        (R.drawable.iphone),
        (R.drawable.iphone2),
        (R.drawable.iphone3),
        (R.drawable.iphone),
    )

    val specificationsList = listOf<Specification>(
        Specification("Storage", "256gb"),
        Specification("Color", "Deep Purple"),
        Specification("Battery health", "98%"),
        Specification("Carrier", "Unlocked"),
    )

    val pagerState = rememberPagerState(pageCount = { productImages.size })

    val s5 = 18
    val s4 = 4
    val s3 = 1
    val s2 = 1
    val s1 = 0

    val max = s5+s4+s3+s2+s1


    Scaffold(
        topBar = {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            //navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                //navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.heart_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                //navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.share_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp)
                            )
                        }
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
    ) {innerPadding->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Box(){
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        ) {image->

                            Image(
                                painter = painterResource(productImages[image]),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {}
                            Row (
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                // 🔥 Smart Dots Indicator
                                if (productImages.size > 1) {

                                    val totalDots = productImages.size
                                    val currentPage = pagerState.currentPage
                                    val maxVisibleDots = 5

                                    val startIndex = when {
                                        totalDots <= maxVisibleDots -> 0
                                        currentPage <= 2 -> 0
                                        currentPage >= totalDots - 3 -> totalDots - maxVisibleDots
                                        else -> currentPage - 2
                                    }

                                    val endIndex = minOf(startIndex + maxVisibleDots, totalDots)



                                    Row(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        val hasPrevious = startIndex > 0
                                        val hasNext = endIndex < totalDots

                                        for (i in startIndex until endIndex) {

                                            val dotSize = when {
                                                i == currentPage -> 10.dp
                                                i == startIndex && hasPrevious -> 4.dp
                                                i == endIndex - 1 && hasNext -> 4.dp
                                                else -> 7.dp
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .size(dotSize)
                                                    .clip(CircleShape)
                                                    .background(
                                                        if (i == currentPage)
                                                            Blue
                                                        else
                                                            White.copy(alpha = 0.5f)
                                                    )
                                            )
                                        }

                                    }
                                }

                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                            }
                        }
                    }

                }

                item{
                    LazyRow(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp
                        )
                    ) {
                        items(productImages){image->
                            Image(
                                painter = painterResource(image),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(100.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        1.dp,
                                        Transparent,
                                        RoundedCornerShape(12.dp)
                                    )
                            )
                        }
                    }

                    HorizontalDivider()
                }

                item{
                    Column() {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$${originalPrice-(originalPrice*discount)/100}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "$$originalPrice",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                style = TextStyle(
                                    textDecoration = TextDecoration.LineThrough
                                ),
                                color = GreyTxt
                            )

                            Column(
                                modifier = Modifier
                                    .background(
                                        LLGreen,
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    text = "$discount% OFF",
                                    color = Green,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(
                                        vertical = 4.dp,
                                        horizontal = 8.dp)
                                )
                            }


                        }

                        Text(
                            text = "$name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.star_svgrepo_com),
                                contentDescription = "",
                                tint = Gold,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "$rating ($reviews reviews)",
                                fontSize = 18.sp,
                                color = GreyTxt
                            )
                        }

                    }
                }



                //Product Description
                item {
                    Column() {
                        Text(
                            text = "Description",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = stringResource(R.string.demoDescription),
                            fontSize = 16.sp,
                            color = GreyTxt,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp, bottom = 16.dp)
                        )
                    }


                }

                //Specifications Card
                item{
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "Description",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            specificationsList.forEach {specification->
                                Row(
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${specification.parameter}: ",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = specification.value,
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }

                //Reviews
                item{
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Reviews ($reviews)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LGrey
                                ),
                                modifier = Modifier.border(
                                    1.dp,
                                    Grey,
                                    RoundedCornerShape(12.dp)
                                ),
                                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Write a review",
                                    fontSize = 16.sp,
                                    color = Black
                                )
                            }
                        }

                        //Review Card

                        Card(
                            modifier = Modifier.padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = LGrey
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = rating.toString(),
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Row() {
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Text(
                                        text = "$reviews reviews",
                                        fontSize = 16.sp,
                                        color = GreyTxt
                                    )
                                }

                                Column(
                                    modifier = Modifier.padding(start = 16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "5",
                                            color = GreyTxt
                                        )

                                        Spacer(Modifier.width(4.dp))

                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(16.dp)
                                        )

                                        Spacer(Modifier.width(8.dp))

                                        CustomProgressBar2(
                                            value = s5.toFloat(),
                                            maxValue = max.toFloat(),
                                            modifier = Modifier.weight(1f)   // 🔥 THIS IS THE KEY
                                        )

                                        Spacer(Modifier.width(8.dp))

                                        Text(
                                            text = s5.toString(),
                                            color = GreyTxt,
                                            modifier = Modifier.width(28.dp), // fixed width so it never wraps
                                            maxLines = 1
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "4",
                                            color = GreyTxt
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        CustomProgressBar2(
                                            value = s4.toFloat(),
                                            maxValue = max.toFloat(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            text = s4.toString(),
                                            color = GreyTxt,
                                            modifier = Modifier.width(28.dp), // fixed width so it never wraps
                                            maxLines = 1
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "3",
                                            color = GreyTxt
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        CustomProgressBar2(
                                            value = s3.toFloat(),
                                            maxValue = max.toFloat(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            text = s3.toString(),
                                            color = GreyTxt,
                                            modifier = Modifier.width(28.dp), // fixed width so it never wraps
                                            maxLines = 1
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "2",
                                            color = GreyTxt
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        CustomProgressBar2(
                                            value = s2.toFloat(),
                                            maxValue = max.toFloat(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            text = s2.toString(),
                                            color = GreyTxt,
                                            modifier = Modifier.width(28.dp), // fixed width so it never wraps
                                            maxLines = 1
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "1",
                                            color = GreyTxt
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(R.drawable.star_svgrepo_com),
                                            contentDescription = "",
                                            tint = Gold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        CustomProgressBar2(
                                            value = s1.toFloat(),
                                            maxValue = max.toFloat(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            text = s1.toString(),
                                            color = GreyTxt,
                                            modifier = Modifier.width(28.dp), // fixed width so it never wraps
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                items(4){
                    ReviewItem(
                        name = "David Lee",
                        timeAgo = "1 month ago",
                        rating = 4,
                        reviewText = "Great condition as described. Minor scuff on the back that wasn't mentioned but overall very happy with the purchase. Would buy from this seller again.",
                        helpfulCount = 8,
                        profileImage = R.drawable.rectangle_36__2_
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(bottom = 20.dp)
                    .background(BackgroundColor),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HorizontalDivider()
                Row() {
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(40.dp)
                            .weight(1f)
                            .padding(horizontal = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LGrey
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.cart_shopping_svgrepo_com),
                                contentDescription = "",
                                tint = Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                "Add to cart",
                                color = Black
                            )
                        }

                    }
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(40.dp)
                            .weight(1f)
                            .padding(end = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text(
                            "Buy now",
                            color = White
                        )


                    }
                }
                HorizontalDivider()

            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview(){
    ProductScreen()
}