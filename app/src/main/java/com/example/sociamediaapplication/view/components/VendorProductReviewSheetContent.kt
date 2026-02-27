package com.example.sociamediaapplication.view.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel

@Composable
fun VendorProductReviewSheetContent(
    context: Context,
    productId: Int,
    viewModel: MarketplaceViewModel
) {

    val reviews by viewModel.productReviews.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProductReviews(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Product Reviews",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        reviews?.reviews?.forEach { review ->

            VendorReviewReplyItem(
                name = review.username,
                timeAgo = review.created_at,
                rating = review.rating,
                reviewText = review.comment,
                profileImage = R.drawable.rectangle_36__2_,
                existingReply = review.vendor_reply,
                existingReplyTime = review.vendor_reply_at,
                onSendReply = { reply ->
                    viewModel.addReviewReply(
                        context = context,
                        reviewId = review.id,
                        reply = reply
                    )
                }
            )

            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(40.dp))
    }
}