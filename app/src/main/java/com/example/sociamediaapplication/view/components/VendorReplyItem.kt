package com.example.sociamediaapplication.view.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Gold

@Composable
fun VendorReviewReplyItem(
    name: String = "John Doe",
    timeAgo: String = "5m ago",
    rating: Int = 4,
    reviewText: String = "Review Text",
    profileImage: Int = R.drawable.rectangle_5,
    existingReply: String? = null,
    existingReplyTime: String? = null,
    isSending: Boolean = false,
    onSendReply: (String) -> Unit = {}
) {

    var showReplyBox by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        // 🔹 Show User Review (reuse your existing ReviewItem)
        ReviewItem(
            name = name,
            timeAgo = timeAgo,
            rating = rating,
            reviewText = reviewText,
            profileImage = profileImage,
            vendor_reply = null, // vendor side shouldn't auto show reply
            isVendor = true,
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 🔹 Reply Button
        Row(
            modifier = Modifier.padding(start = 60.dp)
        ) {
            Text(
                text = if (showReplyBox) "Cancel" else "Reply",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Gold,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .padding(vertical = 4.dp)
                    .clickable {
                        showReplyBox = !showReplyBox
                    }
            )
        }

        // 🔹 Reply Input Section
        if (showReplyBox) {

            ReplyReviewSection(
                existingReply = existingReply,
                existingReplyTime = existingReplyTime,
                isSending = isSending,
                onSendReply = {
                    onSendReply(it)
                    showReplyBox = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VendorReplyItemPreview(){
    VendorReviewReplyItem()
}