package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.response.VideoComment
import com.example.sociamediaapplication.ui.theme.TTransparentWhite
import com.example.sociamediaapplication.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagePost(
    uName: String = "John Doe",
    sincePosted: String = "1m",
    isVerified: Boolean = false,
    caption: String = "",
    mediaList: List<Int>? = emptyList(),
    isFollowing: Boolean = false,
    postLikes: Int = 20,
    onLiked: () -> Unit = {},
    onFollow: () -> Unit = {},
    isLiked: Boolean = false,
    onPostProfileClick: ()-> Unit = {},
    comments: List<VideoComment> = emptyList(),
    onCommentsRequested: () -> Unit = {},
    onAddComment: (String, Int?) -> Unit = { _, _ -> },
    onLikeComment: (Int) -> Unit = {},
    onDislikeComment: (Int) -> Unit = {}
){
    val size = mediaList?.size



    val pagerState = rememberPagerState(pageCount = { mediaList?.size ?: 0 })


    var showCommentSection by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var showSendSection by remember { mutableStateOf(false) }

    var showDropDownMenu by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults
            .cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {

            Spacer(modifier = Modifier.height(8.dp))
            //Caption
            Text(
                text = caption
            )
            Spacer(modifier = Modifier.height(8.dp))

            if(mediaList?.isNotEmpty() == true){
                Box(
                    modifier = Modifier.aspectRatio(1f)
                ){
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) { page ->

                        Image(
                            painter = painterResource(mediaList[page]),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {}
                        Row (
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // 🔥 Smart Dots Indicator
                            if (mediaList.size > 1) {

                                val totalDots = mediaList.size
                                val currentPage = pagerState.currentPage
                                val maxVisibleDots = 5

                                val startIndex = when {
                                    totalDots <= maxVisibleDots -> 0
                                    currentPage <= 2 -> 0
                                    currentPage >= totalDots - 3 -> totalDots - maxVisibleDots
                                    else -> currentPage - 2
                                }

                                val endIndex = minOf(startIndex + maxVisibleDots, totalDots)



                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    val hasPrevious = startIndex > 0
                                    val hasNext = endIndex < totalDots

                                    for (i in startIndex until endIndex) {

                                        val dotSize = when {
                                            i == currentPage -> 10.dp
                                            i == startIndex && hasPrevious -> 4.dp
                                            i == endIndex - 1 && hasNext -> 4.dp
                                            else -> 7.dp
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(dotSize)
                                                .clip(CircleShape)
                                                .background(
                                                    if (i == currentPage)
                                                        White
                                                    else
                                                        White.copy(alpha = 0.5f)
                                                )
                                        )
                                    }

                                }
                            }

                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        color = TTransparentWhite,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .width(40.dp)
                                    .height(20.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${pagerState.currentPage+1}/$size")
                            }
                        }
                    }
                }
            }



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Row(
                    modifier = Modifier
                        .width(140.dp)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                //Like Button
                                onLiked()
                            },
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(0.dp))
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isLiked) R.drawable.like_svgrepo_com__1_ else R.drawable.like_svgrepo_com
                                ),
                                contentDescription = "",
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                        Text(
                            text = postLikes.toString(),
                            fontSize = 18.sp
                        )
                    }
                    IconButton(
                        onClick = {
                            showCommentSection = !showCommentSection
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(0.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.comment_outlined_3_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            showSendSection = !showSendSection
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(0.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.send_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

            }

        }
    }

    if(showCommentSection){
        LaunchedEffect(Unit) {
            onCommentsRequested()
        }
        ModalBottomSheet(
            onDismissRequest = {showCommentSection = false},
            sheetState = sheetState,
            containerColor = White
        ) {
            CommentSection(
                comments = comments,
                onAddComment = onAddComment,
                onLikeComment = onLikeComment,
                onDislikeComment = onDislikeComment
            )
        }
    }
    if (showSendSection) {
        AlertDialog(
            onDismissRequest = {showSendSection = false},
            confirmButton = {
            },
            text = {
                SendSection()
            },
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(0.95f),
            containerColor = White,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PagePostPreview(){
    PagePost(
        caption = "🚀 Just published our in-depth review of the latest flagship phone! " +
                "Check out the full breakdown of camera and battery life.",
    )
}