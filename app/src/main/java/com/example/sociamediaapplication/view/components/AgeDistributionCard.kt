package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun AgeDistributionCard(
    agePopulation1: Float = 17f,
    agePopulation2: Float = 32f,
    agePopulation3: Float = 25f,
    agePopulation4: Float = 18f,
    agePopulation5: Float = 8f
) {

    val totalPopulation: Float = agePopulation1+agePopulation2+agePopulation2+agePopulation4+agePopulation5

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.team_3),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Age Distribution",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Under 18",
                        color = GreyTxt
                    )
                    Text(
                        text = "$agePopulation1%"
                    )
                }
                CustomProgressBar2(
                    maxValue = totalPopulation,
                    value = agePopulation1
                )
            }
            Column(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "18-24",
                        color = GreyTxt
                    )
                    Text(
                        text = "$agePopulation2%"
                    )
                }
                CustomProgressBar2(
                    maxValue = totalPopulation,
                    value = agePopulation2
                )
            }
            Column(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "25-34",
                        color = GreyTxt
                    )
                    Text(
                        text = "$agePopulation3%"
                    )
                }
                CustomProgressBar2(
                    maxValue = totalPopulation,
                    value = agePopulation3
                )
            }
            Column(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "35-44",
                        color = GreyTxt
                    )
                    Text(
                        text = "$agePopulation4%"
                    )
                }
                CustomProgressBar2(
                    maxValue = totalPopulation,
                    value = agePopulation4
                )
            }
            Column(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "45+",
                        color = GreyTxt
                    )
                    Text(
                        text = "$agePopulation5%"
                    )
                }
                CustomProgressBar2(
                    maxValue = totalPopulation,
                    value = agePopulation5
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AgeDistributionCardPreview(){
    AgeDistributionCard()
}