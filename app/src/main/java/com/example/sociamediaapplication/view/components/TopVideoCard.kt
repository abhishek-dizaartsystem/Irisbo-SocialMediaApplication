package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.model.response.TopVideo
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun TopVideoCard(
    video: TopVideo
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = correctUrl(video.thumbnail_url),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        width = 120.dp,
                        height = 70.dp
                    )
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = video.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Performance Score: ${video.performance_score}",
                    color = Green,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${video.views_count} views",
                    color = GreyTxt
                )

                Text(
                    text = "${video.likes_count} likes",
                    color = GreyTxt
                )
            }
        }
    }
}