package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.model.response.VideoAnalytics2
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun VideoPerformanceItem(
    video: VideoAnalytics2
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            AsyncImage(
                model = correctUrl(video.thumbnail_url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = video.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                AnalyticsStat(
                    icon = R.drawable.eye_outlined_svgrepo_com,
                    value = video.views_count.toString()
                )

                AnalyticsStat(
                    icon = R.drawable.heart_svgrepo_com,
                    value = video.likes_count.toString()
                )

                AnalyticsStat(
                    icon = R.drawable.chat_dots_svgrepo_com,
                    value = video.comments_count.toString()
                )

                AnalyticsStat(
                    icon = R.drawable.share_svgrepo_com,
                    value = video.shares_count.toString()
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text =
                    "Watch Time: ${video.total_watch_time_seconds}s",
                color = GreyTxt
            )

            Text(
                text =
                    "Avg Watch Time: ${video.avg_watch_time_seconds}s",
                color = GreyTxt
            )

            Text(
                text =
                    "Completion: ${video.completion_rate}%",
                color = GreyTxt
            )

            Text(
                text =
                    "Engagement: ${video.engagement_rate}%",
                color = GreyTxt
            )
        }
    }
}

@Composable
fun AnalyticsStat(
    icon: Int,
    value: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = GreyTxt
        )

        Spacer(
            modifier = Modifier.width(4.dp)
        )

        Text(
            text = value,
            color = GreyTxt
        )
    }
}