package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.data.utils.correctUrl

@Composable
fun VideoThumbnail3(
    title: String,
    imageUrl: String?,
    onVideoClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onVideoClick()
            }
    ) {

        AsyncImage(
            model = correctUrl(imageUrl ?: ""),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f)
                .clip(
                    RoundedCornerShape(12.dp)
                ),
            contentScale = ContentScale.Crop
        )

        Text(
            text = title,
            modifier = Modifier.padding(
                top = 8.dp
            ),
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}