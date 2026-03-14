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
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun PaymentsItem(){
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
                    text = "$450.00",
                    fontSize = 18.sp,
                )
                Text(
                    text = "Jan 15, 2025",
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
                    text = "Completed",
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
    PaymentsItem()
}