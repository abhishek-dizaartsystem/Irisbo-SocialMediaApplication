package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun ApplicantItem(
    profileImage: String = "https://picsum.photos/200",
    name: String = "John Doe",
    appliedOn: String = "Jan 22, 2026",
    status: String = "New",
    onApplicantClick: () -> Unit = {}
){
    Card(
        onClick = onApplicantClick,
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = profileImage,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp).clip(HexagonShape),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        Modifier.padding(start = 8.dp)
                    ) {
                        Text(name, fontSize = 16.sp)
                        Text("Applied on $appliedOn", color = GreyTxt)
                    }
                }

                Row(
                    modifier = Modifier
                        .background(LLBlue, RoundedCornerShape(16.dp))
                        .border(1.dp, LBlue, RoundedCornerShape(16.dp))
                ) {
                    Text(status, color = Blue,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicantItemPreview(){
    ApplicantItem()
}