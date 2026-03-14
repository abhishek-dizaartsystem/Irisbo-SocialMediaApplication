package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun ReplyReviewSection(
    existingReply: String? = null,
    existingReplyTime: String? = null,
    isSending: Boolean = false,
    onSendReply: (String) -> Unit = {}
) {

    var showReplyBox by remember { mutableStateOf(false) }
    var replyText by remember { mutableStateOf(existingReply ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 48.dp, end = 16.dp, top = 8.dp)
    ) {

        // If reply already exists → show it
        if (!existingReply.isNullOrBlank()) {

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = LGrey)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Your Reply",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )

                        Text(
                            text = existingReplyTime ?: "",
                            fontSize = 12.sp,
                            color = GreyTxt
                        )
                    }

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = existingReply,
                        fontSize = 14.sp,
                        color = GreyTxt
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
        }

        // Input Field
        Row(verticalAlignment = Alignment.CenterVertically) {

            TextField(
                value = replyText,
                onValueChange = { replyText = it },
                placeholder = {
                    Text("Write a reply...", color = GreyTxt)
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    focusedContainerColor = LGrey,
                    unfocusedContainerColor = LGrey
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .heightIn(48.dp, 200.dp)
            )

            Spacer(Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (replyText.isNotBlank() && !isSending) {
                        onSendReply(replyText)
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                if (isSending) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.send_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier
                            .size(26.dp)
                            .rotate(15f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyReviewSectionPreview(){
    ReplyReviewSection()
}