package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Gold
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun ReviewItem(
    name: String,
    timeAgo: String,
    rating: Int,
    reviewText: String,
    helpfulCount: Int,
    profileImage: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Top Row (Image + Name + Time)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painter = painterResource(profileImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Row {
                            repeat(5) { index ->
                                Icon(
                                    painter = painterResource(R.drawable.star_svgrepo_com),
                                    contentDescription = null,
                                    tint = if (index < rating) Gold else LGrey,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                Text(
                    text = timeAgo,
                    fontSize = 14.sp,
                    color = GreyTxt
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Review Text
            Text(
                text = reviewText,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = GreyTxt
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Helpful Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.like_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = GreyTxt
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Helpful ($helpfulCount)",
                    fontSize = 14.sp,
                    color = GreyTxt
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewItemPreview() {
    ReviewItem(
        name = "David Lee",
        timeAgo = "1 month ago",
        rating = 4,
        reviewText = "Great condition as described. Minor scuff on the back that wasn't mentioned but overall very happy with the purchase. Would buy from this seller again.",
        helpfulCount = 8,
        profileImage = R.drawable.rectangle_36__2_
    )
}
