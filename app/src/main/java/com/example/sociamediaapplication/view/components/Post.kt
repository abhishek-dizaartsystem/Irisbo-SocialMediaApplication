package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.formatPostTime
import com.example.sociamediaapplication.data.utils.isVideo
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.TTransparentWhite
import com.example.sociamediaapplication.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post(
    uName: String = "John Doe",
    isVerified: Boolean = false,
    caption: String = "",
    mediaList: List<Any>? = listOf(
        (R.drawable.rectangle_6),
        (R.drawable.rectangle_5),
        (R.drawable.rectangle_24)
    ),
    isFollowing: Boolean = false,
    postLikes: Int = 20,
    onLiked: () -> Unit = {},
    onFollow: () -> Unit = {},
    onSaved: () -> Unit = {},
    onDelete: () -> Unit = {},
    isSaved: Boolean = false,
    isLiked: Boolean = false,
    onPostProfileClick: ()-> Unit = {},
    profileImageUrl: String? = null,
    createdAt: String = "",
    type: String = "post"
){

    val sincePosted = remember(createdAt) {
        formatPostTime(createdAt)
    }

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
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            onPostProfileClick()
                        },
                        modifier = Modifier
                            .size(50.dp) // Set the size of the clickable area
                            .border(
                                width = 1.dp,
                                color = Black,
                                shape = HexagonShape
                            )
                    ) {
                        AsyncImage(
                            model = profileImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(HexagonShape)
                        )

                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = uName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            if(isVerified){
                                Icon(
                                    painter = painterResource(R.drawable.verified_check_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(2.dp),
                                    tint = Blue
                                )
                            }
                            if(type != "group post"){
                                Button(
                                    onClick = {onFollow()},
                                    contentPadding = PaddingValues(
                                        vertical = 0.dp,
                                        horizontal = 12.dp
                                    ),
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp)
                                        .height(24.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Blue
                                    )
                                ) {
                                    Text(
                                        text = if(isFollowing) "Unfollow" else "Follow"
                                    )
                                }

                            }

                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = sincePosted
                            )
                            Icon(
                                painter = painterResource(R.drawable.world_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = 4.dp)
                            )
                        }

                    }

                }
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {showDropDownMenu = true}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = showDropDownMenu,
                        onDismissRequest = {showDropDownMenu = false},
                        containerColor = White
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("Report")
                            },
                            onClick = {showDropDownMenu = false},
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = if(isSaved) "Unsave" else "Save"
                                )
                            },
                            onClick = {

                                onSaved()
                                showDropDownMenu = false

                            },
                        )
                        DropdownMenuItem(
                            text = {
                                Text("Delete")
                            },
                            onClick = {
                                showDropDownMenu = false
                                onDelete()
                            },
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
            //Caption
            Text(
                text = caption
            )
            Spacer(modifier = Modifier.height(8.dp))

            if(size!=null){
                Box(
                    modifier = Modifier.aspectRatio(1f)
                ){
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) { page ->

                        val media = mediaList[page]

                        when (media) {

                            is Int -> {
                                Image(
                                    painter = painterResource(media),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                )
                            }

                            is String -> {

                                if (isVideo(media)) {

                                    AutoVideo(
                                        url =
                                            if(type == "group post")
                                                "${RetrofitClient.BASE_URL}$media"
                                            else
                                                media,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                    )

                                } else {

                                    AsyncImage(
                                        model =
                                            if (type == "group post")
                                                "${RetrofitClient.BASE_URL}$media"
                                            else
                                                media,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                    )
                                }
                            }
                        }

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
        ModalBottomSheet(
            onDismissRequest = {showCommentSection = false},
            sheetState = sheetState,
            containerColor = White
        ) {
            CommentSection()
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
fun PostPreview(){
    Post(
        caption = "Springs bright ,all sustainable !" +
                "Everything shown was made before 1982,except vintage",
    )
}