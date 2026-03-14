package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Gold
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun ReviewItem(
    name: String,
    timeAgo: String,
    rating: Int,
    reviewText: String,
    profileImage: Int,
    modifier: Modifier = Modifier,
    vendor_reply: String? = "null",
    vendor_reply_at: String? = "null",
    review_likes: Int = 0,
    review_dislikes: Int = 0,
    reply_likes: Int = 0,
    reply_dislikes: Int = 0,
    user_review_reaction: String? = null,
    user_reply_reaction: String? = null,
    onReviewLiked: () -> Unit ={},
    onReviewDisliked: () -> Unit = {},
    onReplyLiked: () -> Unit = {},
    onReplyDisliked: () -> Unit = {},
    isVendor: Boolean = false
) {

    var showReply by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
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
                            .clip(HexagonShape)
                            .border(1.dp, Black),
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

            // Helpful Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if(!isVendor){
                    Button(
                        onClick = onReviewLiked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = if(user_review_reaction == "like")
                                painterResource(R.drawable.like_svgrepo_com__1_)
                            else
                                painterResource(R.drawable.like_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = GreyTxt
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "Helpful ($review_likes)",
                            fontSize = 14.sp,
                            color = GreyTxt
                        )
                    }
                    Button(
                        onClick = onReviewDisliked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = if(user_review_reaction == "dislike")
                                painterResource(R.drawable.like_svgrepo_com__1_)
                            else
                                painterResource(R.drawable.like_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp).rotate(180f),
                            tint = GreyTxt
                        )
                        Text(
                            text = "($review_dislikes)",
                            fontSize = 14.sp,
                            color = GreyTxt
                        )
                    }
                }
                Button(
                    onClick = {
                        if (!vendor_reply.isNullOrBlank() && vendor_reply != "null") {
                            showReply = !showReply
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (showReply) "Hide replies" else "Replies",
                        fontSize = 14.sp,
                        color = GreyTxt,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.back_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier
                            .size(12.dp)
                            .rotate(if (showReply) 90f else -90f),
                        tint = GreyTxt
                    )
                }

            }
            if (showReply && !vendor_reply.isNullOrBlank() && vendor_reply != "null") {

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = LGrey
                    ),
                    modifier = Modifier.padding(start = 40.dp) // indent reply
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Vendor",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )

                            Text(
                                text = vendor_reply_at ?: "",
                                fontSize = 12.sp,
                                color = GreyTxt
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = vendor_reply ?: "",
                            fontSize = 14.sp,
                            color = GreyTxt
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Reply Like
                            Button(
                                onClick = onReplyLiked,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Transparent
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    painter = if(user_reply_reaction == "liked")
                                        painterResource(R.drawable.like_svgrepo_com__1_)
                                    else
                                        painterResource(R.drawable.like_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp),
                                    tint = GreyTxt
                                )
                                Text(
                                    "$reply_likes",
                                    fontSize = 12.sp,
                                    color = GreyTxt
                                )
                            }


                            // Reply Dislike

                            Button(
                                onClick = onReplyDisliked,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Transparent
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    painter = if(user_reply_reaction == "disliked")
                                        painterResource(R.drawable.like_svgrepo_com__1_)
                                    else
                                        painterResource(R.drawable.like_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .rotate(180f),
                                    tint = GreyTxt
                                )
                                Text(
                                    "$reply_dislikes",
                                    fontSize = 12.sp,
                                    color = GreyTxt
                                )
                            }

                        }
                    }
                }
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
        profileImage = R.drawable.rectangle_36__2_,
    )
}
