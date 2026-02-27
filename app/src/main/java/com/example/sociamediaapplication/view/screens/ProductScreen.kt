package com.example.sociamediaapplication.view.screens

import android.graphics.Bitmap
import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.formatPostTime
import com.example.sociamediaapplication.data.utils.getFrameFromUrl
import com.example.sociamediaapplication.data.utils.isVideo
import com.example.sociamediaapplication.model.CartItem
import com.example.sociamediaapplication.model.Specification
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Gold
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLGreen
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.AddReviewSection
import com.example.sociamediaapplication.view.components.AutoVideo
import com.example.sociamediaapplication.view.components.CommentSection
import com.example.sociamediaapplication.view.components.CustomProgressBar2
import com.example.sociamediaapplication.view.components.ReviewItem
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productId: String = "1",
    navController: NavController = rememberNavController(),
    viewModel: MarketplaceViewModel = viewModel(),
){


    val productDetails by viewModel.productDetails.collectAsState()

    val productReviews by viewModel.productReviews.collectAsState()

    val pagerState = rememberPagerState(
        pageCount = { productDetails?.product?.product_images?.size ?: 0 }
    )

    var showReviewSection by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val reviewsList = productReviews?.reviews ?: emptyList()

    val s5 = reviewsList.count { it.rating == 5 }
    val s4 = reviewsList.count { it.rating == 4 }
    val s3 = reviewsList.count { it.rating == 3 }
    val s2 = reviewsList.count { it.rating == 2 }
    val s1 = reviewsList.count { it.rating == 1 }

    val max = reviewsList.size

    val context = LocalContext.current



    Scaffold(
        topBar = {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
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
                        ) {page->

                            val mediaUrl = "${RetrofitClient.BASE_URL}uploads/${productDetails?.product?.product_images?.getOrNull(page)}"

                            when{
                                mediaUrl == null -> {}

                                mediaUrl.endsWith(".mp4", true)->{
                                    AutoVideo(
                                        url = mediaUrl,
                                    )
                                }

                                else->{
                                    AsyncImage(
                                        model = mediaUrl,
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                    )
                                }
                            }

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


                                val totalDots = productDetails?.product?.product_images?.size ?: 0
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        productDetails?.product?.product_images?.let { images ->
                            items(images){image->
                                val mediaUrl = "${RetrofitClient.BASE_URL}uploads/$image"

                                val thumbnail: Bitmap? = remember(mediaUrl) {
                                    if (isVideo(mediaUrl))
                                        getFrameFromUrl(context, mediaUrl)
                                    else null
                                }

                                AsyncImage(
                                    model = thumbnail ?: mediaUrl,
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
                                text = "$${productDetails?.product?.discounted_price}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "$${productDetails?.product?.price}",
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
                                    text = "${productDetails?.product?.discount}% OFF",
                                    color = Green,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(
                                        vertical = 4.dp,
                                        horizontal = 8.dp)
                                )
                            }


                        }

                        Text(
                            text = "${productDetails?.product?.name}",
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
                                text = "(${productReviews?.total_reviews} reviews)",
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
                            text = "Description $productId",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = "${productDetails?.product?.description}",
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
                                text = "Specifications",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            productDetails?.product?.specifications?.forEach {specification->
                                Row(
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${specification.key}: ",
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
                                text = "Reviews (${productReviews?.total_reviews})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Button(
                                onClick = {
                                    showReviewSection = true
                                },
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
                                        text = "${productDetails?.product?.average_rating}",
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
                                        text = "${productReviews?.total_reviews} reviews",
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
                                            maxValue = productReviews?.total_reviews?.toFloat() ?: 0f,
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
                                            maxValue = productReviews?.total_reviews?.toFloat() ?: 0f,
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
                                            maxValue = productReviews?.total_reviews?.toFloat() ?: 0f,
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
                                            maxValue = productReviews?.total_reviews?.toFloat() ?: 0f,
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
                                            maxValue = productReviews?.total_reviews?.toFloat() ?: 0f,
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

                items(productReviews?.reviews?.size ?: 0){review->
                    ReviewItem(
                        name = productReviews?.reviews?.get(review)?.username ?: "",
                        timeAgo = formatPostTime(productReviews?.reviews?.get(review)?.created_at?: ""),
                        rating = productReviews?.reviews?.get(review)?.rating ?: 3,
                        reviewText = productReviews?.reviews?.get(review)?.comment ?: "",
                        helpfulCount = 8,
                        profileImage = R.drawable.rectangle_36__2_
                    )
                }
                item {
                    Column(Modifier.height(90.dp)) { }
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(BackgroundColor),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HorizontalDivider()
                Row() {
                    Button(
                        onClick = {
                            try {
                                viewModel.addToCart(
                                    productId = productId,
                                    onError = {error->
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    }
                                )
                                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                            }
                            catch (error: Exception){
                                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                            }




                        },
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

    if(showReviewSection){
        ModalBottomSheet(
            onDismissRequest = {showReviewSection = false},
            sheetState = sheetState,
            containerColor = White
        ) {
            AddReviewSection(
                onSendReview = {review, rating->
                    Toast.makeText(context, review, Toast.LENGTH_SHORT).show()
                    showReviewSection = false
                    viewModel.addProductReview(
                        productId = productDetails?.product?.id ?: 0,
                        rating = rating,
                        comment = review,
                        context = context
                    )

                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview(){
    ProductScreen()
}