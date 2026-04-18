package com.example.sociamediaapplication.view.screens

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.correctUrl2
import com.example.sociamediaapplication.data.utils.getFrameFromUrl
import com.example.sociamediaapplication.data.utils.isVideo
import com.example.sociamediaapplication.model.response.PostResponse
import com.example.sociamediaapplication.model.response.Reel
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DBlue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.view.components.Post
import com.example.sociamediaapplication.view.components.ZoomableImageDialog
import com.example.sociamediaapplication.viewmodel.FriendViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun OtherProfileScreen(
    onChatClick: () -> Unit = {},
    friendViewModel: FriendViewModel = viewModel(),
    posts: List<PostResponse> = emptyList(),
    reels: List<Reel> = emptyList(),
    profileViewModel: ProfileViewModel = viewModel(),
    onReelLike: (Reel) -> Unit = {},
    onReelSave: (Reel) -> Unit = {},
    onPostLike: (PostResponse) -> Unit = {},
    onPostSave: (PostResponse) -> Unit = {},
    userId: Int = 0
) {

    var postSelected by remember { mutableStateOf(true) }

    val userPosts = remember { List(15) { R.drawable.rectangle_24 } }

    var selectedReelIndex by remember { mutableStateOf<Int?>(null) }

    var selectedPostId by remember { mutableStateOf<Int?>(null) }

    var showImage by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<Any?>(null) }

    val profile by profileViewModel.otherProfile.collectAsState()

    val friendshipStatus by friendViewModel.friendshipStatus.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = White
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(modifier = Modifier.height(220.dp)) {
                AsyncImage(
                    model = if(profile?.data?.cover_img == null)
                        R.drawable.rectangle_24
                    else
                        correctUrl(profile?.data?.cover_img),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .clickable {
                            selectedImage = if (profile?.data?.cover_img == null)
                                R.drawable.rectangle_24
                            else
                                correctUrl(profile?.data?.cover_img)
                            showImage = true
                        }
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row() {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(134.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = White
                            ),
                            shape = HexagonShape
                        ) {
                            AsyncImage(
                                model = if(profile?.data?.profile_image == null )
                                    R.drawable.profile_image_placeholder
                                else
                                    correctUrl(profile?.data?.profile_image),
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(HexagonShape)
                                    .clickable {
                                        selectedImage = if (profile?.data?.profile_image == null)
                                            R.drawable.profile_image_placeholder
                                        else
                                            correctUrl(profile?.data?.profile_image)
                                        showImage = true
                                    }
                            )

                        }
                    }

                }

                Row() {
                    Column() {
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Text(
            text = profile?.data?.name?:"",
            fontSize = 25.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = profile?.data?.bio?:"",
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp, bottom = 20.dp)
        ){
            Button(
                onClick = {
                    when(friendshipStatus?.data?.status){
                        "friends"-> {
                            friendViewModel.unfriendUser(userId)
                        }
                        "pending_sent"-> {
                            friendViewModel.cancelRequest(userId)
                        }
                        "pending_received"-> {
                            friendViewModel.acceptRequest(userId)
                        }
                        "blocked"-> {
                            //No action
                        }
                        "none"-> {
                            friendViewModel.sendFriendRequest(userId)
                        }
                        else -> ""
                    }
//                    if(
//                        friendshipStatus?.data?.status == "pending" ||
//                        friendshipStatus?.data?.status == "accepted"
//                    ){
//
//                    }else{
//                        friendViewModel.sendFriendRequest(userId)
//                    }

                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(friendshipStatus?.data?.status == "none") Blue else LLBlue
                ),
                enabled = friendshipStatus?.data?.status != "blocked"
            ){
                Text(
                    text = when(friendshipStatus?.data?.status){
                        "friends"-> "Unfollow"
                        "pending_sent"->"Cancel Request"
                        "pending_received"->"Accept"
                        "blocked"->"Follow"
                        "none"-> "Follow"
                        else -> ""
                    },
                    color = if(friendshipStatus?.data?.status == "none") White else Black
//                        if(
//                        friendshipStatus?.data?.status == "pending" ||
//                        friendshipStatus?.data?.status == "accepted"
//                    ){
//                        "Unfollow"
//                    }else{
//                        "Follow"
//                    },
//                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    onChatClick()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                border = BorderStroke(1.dp, Grey)
            ){
                Text(
                    text = "Message",
                    fontSize = 16.sp,
                    color = Black
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 Columns
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor),
            contentPadding = PaddingValues(1.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color(0xfff8fafc))
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            postSelected = true
                        },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(postSelected) LBlue else White
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Posts",
                            color = if(postSelected) DBlue else Black
                        )
                    }
                    Button(
                        onClick = {
                            postSelected = false
                        },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(!postSelected) LBlue else White
                        ),
                        contentPadding = PaddingValues(0.dp)

                    ) {
                        Text(
                            text = "Reels",
                            color = if(!postSelected) DBlue else Black
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(color = Grey)
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }){
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Details",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.briefcase_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = GreyTxt
                        )
                        Text(
                            text = "Works at Dizaart",
                            modifier = Modifier
                                .padding(start = 6.dp),
                            color = GreyTxt
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.scholar_cap),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = GreyTxt
                        )
                        Text(
                            text = "Studied at AKTU",
                            modifier = Modifier
                                .padding(start = 6.dp),
                            color = GreyTxt
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.location_pin_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = GreyTxt
                        )
                        Text(
                            text = "Lives in India",
                            modifier = Modifier
                                .padding(start = 6.dp),
                            color = GreyTxt
                        )
                    }
                }
            }
            if (postSelected) {
                items(
                    items = posts,
                    key = { it.id } // 🔥 improves performance
                ) { post ->

                    val context = LocalContext.current

                    val firstMedia = post.media?.firstOrNull()

                    val mediaUrl = firstMedia?.let {
                        if (it.startsWith("http")) it
                        else "${RetrofitClient.BASE_URL}uploads/$it"
                    }

                    val isVideoPost = firstMedia?.let { isVideo(it) } == true

                    // ✅ State for video frame
                    var videoFrame by remember { mutableStateOf<Bitmap?>(null) }

                    // ✅ Load frame in background (NO ANR 🚀)
                    LaunchedEffect(mediaUrl) {
                        if (mediaUrl != null && isVideoPost) {
                            videoFrame = withContext(Dispatchers.IO) {
                                getFrameFromUrl(context, mediaUrl)
                            }
                        } else {
                            videoFrame = null
                        }
                    }

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .clickable { selectedPostId = post.id }
                    ){
                        when{
                            isVideoPost && videoFrame != null ->{
                                Image(
                                    bitmap = videoFrame!!.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                // ▶ play icon
                                Icon(
                                    painter = painterResource(R.drawable.play_svgrepo_com),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(26.dp)
                                )
                            }
                            isVideoPost -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.DarkGray)
                                )
                            }

                            // 🖼 IMAGE
                            mediaUrl != null -> {
                                AsyncImage(
                                    model = mediaUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            // ⚠️ fallback
                            else -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.LightGray)
                                )
                            }
                        }
                    }
                }
            }else {
                itemsIndexed(reels){ index, reel ->

                    val context = LocalContext.current

                    var thumbnail by remember { mutableStateOf<Bitmap?>(null) }

                    LaunchedEffect(reel.video_url) {
                        if (isVideo(reel.video_url)) {
                            thumbnail = withContext(Dispatchers.IO) {
                                getFrameFromUrl(context, correctUrl2(reel.video_url))
                            }
                        } else {
                            thumbnail = null
                        }
                    }

                    Box(
                        modifier = Modifier
                            .aspectRatio(0.5f)
                            .fillMaxWidth()
                            .clickable { selectedReelIndex = index }
                    ) {

                        AsyncImage(
                            model = correctUrl2(reel.video_url),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        val currentThumbnail = thumbnail

                        if (currentThumbnail != null) {
                            Image(
                                bitmap = currentThumbnail.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.DarkGray)
                            )
                        }

                        Icon(
                            painter = painterResource(R.drawable.play_svgrepo_com),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(26.dp)
                        )
                    }
                }
                // Placeholder for Reels

            }


        }




    }
    if (showImage && selectedImage != null) {
        ZoomableImageDialog(
            image = selectedImage!!,
            onDismiss = { showImage = false }
        )
    }

    selectedPostId?.let { id ->

        val post = posts.firstOrNull(){it.id == id}?:return@let
        Dialog(
            onDismissRequest = { selectedPostId = null }
        ) {
            Post(
                uName = post.username ?: "",
                caption = post.caption ?: "",
                mediaList = post.media,
                postLikes = post.likes_count ?: 0,
                onLiked = { onPostLike(post) },
                onSaved = { onPostSave(post) },
                isSaved = post.is_saved,
                isLiked = post.user_reaction == "like",
                profileImageUrl = post.profile_image,
                createdAt = post.created_at,
            )
        }
    }
    selectedReelIndex?.let { startIndex ->




        ReelsScreen(
            reels = reels,
            loading = false,
            startIndex = startIndex,   // 🔥 NEW PARAM
            onLike = onReelLike,
            onSave = onReelSave,
            profileImage = profile?.data?.profile_image,
            userName = profile?.data?.username
        )



    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OtherProfileScreenPreview(){
    OtherProfileScreen()
}