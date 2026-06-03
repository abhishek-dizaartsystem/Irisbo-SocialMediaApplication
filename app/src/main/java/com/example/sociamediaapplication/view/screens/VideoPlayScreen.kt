package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.convertToDuration
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.formatPostTime
import com.example.sociamediaapplication.data.utils.formatToPostTime
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.CommentItem
import com.example.sociamediaapplication.view.components.CommentThread
import com.example.sociamediaapplication.view.components.CustomTextField
import com.example.sociamediaapplication.view.components.DescriptionBottomSheet
import com.example.sociamediaapplication.view.components.VideoFrame
import com.example.sociamediaapplication.view.components.VideoThumbnail
import com.example.sociamediaapplication.viewmodel.VideoViewModel

@Composable
fun VideoPlayScreen(
    videoViewModel: VideoViewModel = viewModel(),
    onVideoClick: (Int) -> Unit = {}
) {

    var showDescriptionBottomSheet by remember { mutableStateOf(false) }
    val video by videoViewModel.video.collectAsState()
    val isFullScreen by videoViewModel.isFullscreen.collectAsState()
    val relatedVideos by videoViewModel.relatedVideos.collectAsState()
    val videoComments by videoViewModel.videoComments.collectAsState()

    var commentText by remember {
        mutableStateOf("")
    }

    var replyingTo by remember {
        mutableStateOf<String?>(null)
    }

    var replyingToCommentId by remember {
        mutableStateOf<Int?>(null)
    }

    val videoList = listOf(
        R.drawable.rectangle_5,
        R.drawable.rectangle_36,
        R.drawable.rectangle_24,
        R.drawable.rectangle_58,
        R.drawable.rectangle_6,
        R.drawable.rectangle_37,
        R.drawable.rectangle_36__2_,
        R.drawable.rectangle_36__1_,
        R.drawable.rectangle_38,
        R.drawable.rectangle_39
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Top Video Player (Not Scrollable)
        VideoFrame(
            videoUrl = correctUrl(video?.data?.video_url),
            isFullscreen = isFullScreen,
            onFullscreenToggle = { videoViewModel.setFullscreen(!isFullScreen) }
        )

        // Everything below scrolls
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Title
            item {
                Text(
                    text = video?.data?.title?:"null",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Views Row
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${video?.data?.views_count} views",
                        color = GreyTxt
                    )
                    Text(
                        text = "${formatToPostTime(video?.data?.created_at?:"")}",
                        modifier = Modifier.padding(start = 6.dp),
                        color = GreyTxt
                    )
                    Button(
                        onClick = {showDescriptionBottomSheet=true},
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(20.dp)
                            .padding(start = 6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Transparent)
                    ){
                        Text(
                            text = "more",
                            modifier = Modifier.padding(start = 6.dp),
                            color = GreyTxt
                        )
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "",
                            tint = GreyTxt
                        )
                    }
                }
            }

            // Action Buttons Row
            item {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {

                    Button(
                        onClick = {
                            videoViewModel.toggleLike(video?.data?.id?:0)
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey)
                    ) {
                        Icon(
                            painter = painterResource(
                                if(video?.data?.viewer_reaction == "like")R.drawable.like_svgrepo_com__1_
                                else R.drawable.like_svgrepo_com
                            ),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
                        Text(
                            text = "${video?.data?.likes_count}",
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Button(
                        onClick = {
                            videoViewModel.toggleDislike(video?.data?.id?:0)
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(
                                if(video?.data?.viewer_reaction == "dislike")R.drawable.like_svgrepo_com__1_
                                else R.drawable.like_svgrepo_com
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(180f),
                            tint = Black
                        )
                        Text(
                            text = "${video?.data?.dislikes_count}",
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(R.drawable.share_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
//                        Text(
//                            text = "${video?.data?.shares_count}",
//                            fontSize = 14.sp,
//                            color = Black,
//                            modifier = Modifier.padding(start = 4.dp)
//                        )
                    }
                    Button(
                        onClick = {
                            videoViewModel.toggleSave(video?.data?.id?:0)
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(
                                if(video?.data?.is_saved == 1) R.drawable.save_filled
                                else R.drawable.save_icon
                            ),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
                    }

                    Button(
                        onClick = {
                            videoViewModel.downloadCurrentVideo()
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                        border = BorderStroke(1.dp, Grey) ) {
                        Icon(
                            painter = painterResource(R.drawable.download_svgrepo_com__1_),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Black
                        )
                    }

                }
            }

            // Divider
            item {
                Spacer( modifier = Modifier.height(8.dp) )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(color = Grey, shape = RoundedCornerShape(4.dp))
                        .padding(vertical = 4.dp)
                )
                Spacer( modifier = Modifier.height(12.dp) )
            }

            // Channel Section
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {

                    AsyncImage(
                        model = correctUrl(video?.data?.creator_profile_image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )

                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "${video?.data?.creator_username}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${video?.data?.subscriber_count} Subscribers",
                            color = GreyTxt,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            videoViewModel.toggleSubscribe(video?.data?.id?:0)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Black)
                    ) {
                        Text(
                            text = if(video?.data?.is_subscribed == 1) "Subscribed" else "Subscribe"
                        )
                    }
                }
            }

            item {
                Spacer( modifier = Modifier.height(8.dp) )
                Spacer( modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Grey, shape = RoundedCornerShape(4.dp))
                    .padding(vertical = 4.dp) )
                Spacer( modifier = Modifier.height(12.dp) )
            }

            // Comments Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${videoComments?.total_comments?: 0} Comments",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.filter_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = GreyTxt
                        )
                        Text(
                            text = "Sort by",
                            fontSize = 14.sp,
                            color = GreyTxt,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }


            //Comment TextField
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {

                    replyingTo?.let {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    GreyBtn,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp),
                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Replying to $it",
                                color = GreyTxt
                            )

                            Text(
                                text = "Cancel",
                                color = Black,
                                modifier = Modifier.clickable {
                                    replyingTo = null
                                    replyingToCommentId = null
                                }
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                    }

                    CustomTextField(
                        label = "",
                        value = commentText,
                        placeHolder =
                            if (replyingTo == null)
                                "Add a comment..."
                            else
                                "Write a reply...",
                        onValueChange = {
                            commentText = it
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {

                            if (commentText.isBlank())
                                return@Button

                            if (replyingTo == null) {

                                videoViewModel.commentOnVideo(
                                    video?.data?.id ?: return@Button,
                                    commentText
                                )

                            } else {

                                // reply API later
                                videoViewModel.commentOnVideo(
                                    video?.data?.id ?: return@Button,
                                    commentText,
                                    replyingToCommentId

                                )
                            }

                            commentText = ""
                            replyingTo = null
                            replyingToCommentId = null
                        },
                        modifier = Modifier.align(
                            Alignment.End
                        )
                    ) {
                        Text(
                            text =
                                if (replyingTo == null)
                                    "Comment"
                                else
                                    "Reply"
                        )
                    }
                }
            }

            // Comments List
            items(
                videoComments?.comments ?: emptyList()
            ) { comment ->

                CommentThread(
                    comment = comment,
                    videoViewModel = videoViewModel,

                    onReply = { selectedComment ->

                        replyingTo =
                            selectedComment.username

                        replyingToCommentId =
                            selectedComment.id
                    }
                )
            }

            item {
                Spacer( modifier = Modifier.height(8.dp) )
                Spacer( modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Grey, shape = RoundedCornerShape(4.dp))
                    .padding(vertical = 4.dp) )
                Spacer( modifier = Modifier.height(12.dp) )
            }

            // Related Videos Grid
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .heightIn(200.dp, 2000.dp), // IMPORTANT: prevent infinite height
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(relatedVideos?.data ?: emptyList()) { video ->

                        Column(
                            modifier = Modifier.clickable {

                                onVideoClick(
                                    video.id
                                )
                            }
                        ) {
                            VideoThumbnail(
                                imageUrl = correctUrl(video.thumbnail_url),
                                text = video.title,
                                channelName = video.creator_username,
                                views = "${video.views_count} views",
                                uploadTime = formatToPostTime(video.created_at),
                                durationTime = convertToDuration(video.duration_seconds)
                            )
                        }

                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    if (showDescriptionBottomSheet) {
        DescriptionBottomSheet(
            onDismiss = {showDescriptionBottomSheet = false}
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideoPlayScreenPreview(){
    VideoPlayScreen()
}
