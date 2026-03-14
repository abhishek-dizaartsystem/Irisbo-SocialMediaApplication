package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Gold
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.TTransparentWhite
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun FeaturedGameItem(
    painter: Painter = painterResource(R.drawable.ludo),
    rating: Float = 4.5f,
    name: String = "Ludo Sling",
    gameType: String = "Puzzle",
    currentOnlinePlayers: String = "13.2K"
){
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(220.dp)
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.aspectRatio(1.7f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = TTransparentWhite,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(14.dp),
                        tint = Gold
                    )
                    Text(
                        text = rating.toString(),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

            }
        }
        Column(
            modifier = Modifier
                .padding(12.dp)
        ){
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = gameType,
                color = GreyTxt
            )
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.team_3),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp),
                    tint = GreyTxt
                )
                Text(
                    text = "$currentOnlinePlayers playing",
                    color = GreyTxt
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeaturedGameItemPreview(){
    FeaturedGameItem()
}