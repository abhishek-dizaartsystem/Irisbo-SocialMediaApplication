package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.model.response.Attachment
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ImageViewerDialog(
    attachments: List<Attachment>,
    startIndex: Int = 0,
    onDismiss: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { attachments.size }
    )

    LaunchedEffect(startIndex) {
        pagerState.scrollToPage(startIndex)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val zoomState = rememberZoomState()
                AsyncImage(
                    model = correctUrl(attachments[page].file_url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .zoomable(zoomState),
                    contentScale = ContentScale.Fit
                )
            }

            // Close button
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.cross_svgrepo_com),
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Page indicator
            if (attachments.size > 1) {
                androidx.compose.material3.Text(
                    text = "${pagerState.currentPage + 1} / ${attachments.size}",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp)
                )
            }
        }
    }
}