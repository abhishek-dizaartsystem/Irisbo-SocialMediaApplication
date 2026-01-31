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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun ApplicationItem(
    position: String = "Frontend Developer",
    companyName: String = "Dizaart Systems",
    appliedOn: String = "Jan 15, 2025"
){
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = position,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier
                        .background(
                            color = LLBlue,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = "Under Review",
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        color = Blue
                    )
                }
            }
            Text(
                text = companyName,
                color = GreyTxt,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            Text(
                text = "Applied on: $appliedOn",
                color = GreyTxt,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicationItemPreview(){
    ApplicationItem()
}