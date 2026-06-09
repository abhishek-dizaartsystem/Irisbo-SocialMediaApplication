package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.data.utils.formatToDate2
import com.example.sociamediaapplication.data.utils.formatToPostTime
import com.example.sociamediaapplication.model.response.Payout
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun PaymentsItem(
    payout: Payout
){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = "₹${payout.amount}",
                    fontSize = 18.sp,
                )

                Text(
                    text = formatToDate2(payout.paid_at ?: payout.created_at),
                    color = GreyTxt
                )
            }
            Row(
                modifier = Modifier
                    .background(
                        color = LBlue,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = payout.status.replaceFirstChar {
                        it.uppercase()
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentsItemPreview(){
    PaymentsItem(
        payout = Payout(
            0,
            0,
            "",
            0,
            0.0,
            "",
            "",
            batch_status = "TODO()",
            failure_reason = "TODO()",
            processed_at = "TODO()",
            paid_at = "TODO()",
            created_at = "TODO()",
        )
    )
}