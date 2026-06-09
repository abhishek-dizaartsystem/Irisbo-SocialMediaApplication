package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.model.response.EarningData
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGreen
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun EarningsItem(
    earning: EarningData
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
                    text = "Order #${earning.order_id}",
                    fontSize = 18.sp,
                )

                Text(
                    text = earning.earning_status.replaceFirstChar {
                        it.uppercase()
                    },
                    color = GreyTxt
                )
            }
            Text(
                text = "₹${earning.seller_net_amount}",
                color = LGreen,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EarningsItemPreview(){
    EarningsItem(
        earning = EarningData(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            platform_gross_amount = 0,
            gateway_fee = 1f,
            seller_fee_share = 1f,
            platform_fee_share = 1f,
            seller_net_amount = 1f,
            platform_net_amount = 1f,
            earning_status = "TODO()",
            payout_eligible_at = "TODO()",
            payment_status = "TODO()",
            order_status = "TODO()",
            created_at = "TODO()",
        )
    )
}