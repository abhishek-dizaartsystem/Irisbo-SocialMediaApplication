package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.UserPostData
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun PostOverlay(
    post: UserPostData,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black.copy(alpha = 0.7f))
        ) {

            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column {

                    AsyncImage(
                        model = post.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = post.caption,
                        modifier = Modifier.padding(16.dp)
                    )

                }
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.cross_svgrepo_com),
                    contentDescription = "",
                    tint = White
                )
            }
        }
    }
}
