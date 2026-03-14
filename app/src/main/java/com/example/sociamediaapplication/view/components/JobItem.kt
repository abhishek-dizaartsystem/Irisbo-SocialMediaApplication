package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobItem(
    painter: Painter = painterResource(R.drawable.react_laptop),
    position: String = "Senior React Developer",
    companyName: String = "Dizaart Systems",
    location: String = "Gurgaon",
    jobType: String = "full-time",
    postedSince: String = "2 days ago",
    salary: String = "$120k - $160k",
    officeType: String = "Remote",
    isSaved: Boolean = false,
    isApplied: Boolean = false,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                    ) {
                        Text(
                            text = position,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = companyName,
                            color = GreyTxt
                        )
                    }
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(2.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            if(isSaved) R.drawable.save_filled else R.drawable.save_icon
                        ),
                        contentDescription = "",
                        tint = GreyTxt,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                }

            }

            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.location_pin_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = GreyTxt
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = location, color = GreyTxt)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.briefcase_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = GreyTxt
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = jobType, color = GreyTxt)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.clock_three_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = GreyTxt
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = postedSince, color = GreyTxt)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row() {
                    Column(
                        modifier = Modifier
                            .background(
                                color = LLBlue,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            text = salary,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            color = Blue
                        )
                    }
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    Column(
                        modifier = Modifier
                            .background(
                                color = Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Grey,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            text = officeType,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue
                    )
                ) {
                    Text(
                        text = "Apply"
                    )
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobItemPreview(){
    JobItem()
}