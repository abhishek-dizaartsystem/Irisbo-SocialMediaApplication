package com.example.sociamediaapplication.view.components

import android.media.browse.MediaBrowser
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.model.UploadMedia
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun UploadItem(
    item: UploadMedia,
    onDelete: ()-> Unit
){
    Box(
        modifier = Modifier.clip(RoundedCornerShape(16.dp))
    ) {
        if(item.mediaType == MediaType.IMAGE){
            AsyncImage(
                model = item.uri,
                contentDescription = "",
                modifier = Modifier.aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }else{
            VideoThumbnail(item.uri)
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                onClick = onDelete,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = DTransparentBlack
                ),
                modifier = Modifier
                    .size(30.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.cross_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(6.dp),
                    tint = White
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UploadItemPreview(){
    val fakeUri = remember {
        Uri.parse("android.resource://com.example.sociamediaapplication/${R.drawable.rectangle_5}")
    }
    UploadItem(
        item = UploadMedia(uri = fakeUri, MediaType.IMAGE),
        onDelete = {},
    )
}