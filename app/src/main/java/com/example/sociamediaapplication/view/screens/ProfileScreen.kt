package com.example.sociamediaapplication.view.screens

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.Post
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    posts: List<PostResponse> = emptyList(),
    reels: List<Reel> = emptyList(),
    onEditProfile: () -> Unit = {},
    onMenu: () -> Unit = {},
    onEditStatus: () -> Unit = {},
    onReelLike: (Reel) -> Unit = {},
    onReelSave: (Reel) -> Unit = {},
    onPostLike: (PostResponse) -> Unit = {},
    onPostSave: (PostResponse) -> Unit = {}
){


    //option
    var postSelected by remember { mutableStateOf(true) }

    var selectedReelIndex by remember { mutableStateOf<Int?>(null) }

    var selectedPostId by remember { mutableStateOf<Int?>(null) }


    val profile by viewModel.profile.collectAsState()
    val context = LocalContext.current


    val userProfileImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri?->
        uri?.let { uri->
            viewModel.uploadProfileImage(uri, context)
        }
    }

    val userCoverImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri?->
        uri?.let { uri->
            viewModel.uploadCoverImage(uri, context)
        }
    }

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

                val url = profile?.data?.cover_img?.removePrefix("/").let {
                    "${RetrofitClient.BASE_URL}$it"
                }


                AsyncImage(
                    model = if(profile?.data?.cover_img == null ) R.drawable.cover_image_placeholder else url,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
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
                    ){
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(134.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = White
                            )
                        ) {
                            AsyncImage(
                                model = if(profile?.data?.profile_image == null ) R.drawable.profile_image_placeholder else "${RetrofitClient.BASE_URL}${profile?.data?.profile_image?.removePrefix("/")}",
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape)
                            )

                        }
//                        IconButton(
//                            onClick = {
//                                userProfileImagePicker.launch("image/*")
//                            },
//                            modifier = Modifier.size(40.dp),
//                            colors = IconButtonDefaults.iconButtonColors(
//                                containerColor = GreyBtn
//                            )
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.camera_svgrepo_com),
//                                contentDescription = "",
//                                modifier = Modifier
//                                    .size(30.dp)
//                            )
//                        }
                    }

                }

                Row() {
                    Column() {
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
//                        IconButton(
//                            onClick = {
//                                userCoverImagePicker.launch("image/*")
//                            },
//                            modifier = Modifier.size(40.dp),
//                            colors = IconButtonDefaults.iconButtonColors(
//                                containerColor = GreyBtn
//                            )
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.camera_svgrepo_com),
//                                contentDescription = "",
//                                modifier = Modifier
//                                    .size(30.dp)
//                            )
//                        }
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
        ) {
            Button(
                onClick = {
                    onEditStatus()
                },
                modifier = Modifier
                    .fillMaxWidth(0.37f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Add Status",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = {
                    onEditProfile()
                },
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                border = BorderStroke(1.dp, Grey)
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit_svgrepo_com__1_),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = Black
                )
                Text(
                    text = "Edit Profile",
                    fontSize = 16.sp,
                    color = Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Button(
                onClick = {
                    onMenu()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreyBtn),
                border = BorderStroke(1.dp, Grey)
            ) {
                Icon(
                    painter = painterResource(R.drawable.menu_navigation_grid_1528_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp),
                    tint = Black
                )
                Text(
                    text = "Menu",
                    fontSize = 16.sp,
                    color = Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        //Posts or Reels

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
                            text = "Works at ${profile?.data?.work}",
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
                            text = "Studied at ${profile?.data?.education}",
                            modifier = Modifier
                                .padding(start = 6.dp),
                            color = GreyTxt
                        )
                    }
                }
            }
            if(postSelected){
                items(
                    items = posts,
                    key = { it.id } // 🔥 improves performance
                ) { post ->

                    val context = LocalContext.current

                    // ✅ Build full media URL
//                    val mediaUrl = post.media?.firstOrNull()?.let {
//                        "${RetrofitClient.BASE_URL}/uploads/$it"
//                    }
//
//                    val isVideoPost = post.media_type == "video"

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
                    ) {

                        when {
                            // 🎥 VIDEO
                            isVideoPost && videoFrame != null -> {
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

                            // ⏳ loading video frame
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
//
//                        // 📸 Multiple media indicator
//                        if ((post.media?.size ?: 0) > 1) {
//                            Icon(
//                                painter = painterResource(R.drawable.gallery_svgrepo_com),
//                                contentDescription = null,
//                                tint = Color.White,
//                                modifier = Modifier
//                                    .align(Alignment.TopEnd)
//                                    .padding(6.dp)
//                                    .size(18.dp)
//                            )
//                        }
                    }
                }
            }
            else{
                itemsIndexed(reels){ index, reel ->

                    val context = LocalContext.current

                    val thumbnail: Bitmap? = remember(reel.video_url) {
                        if (isVideo(reel.video_url))
                            getFrameFromUrl(context, reel.video_url)
                        else null
                    }

                    Box(
                        modifier = Modifier
                            .aspectRatio(0.5f)
                            .fillMaxWidth()
                            .clickable { selectedReelIndex = index }
                    ) {

                        AsyncImage(
                            model = reel.video_url,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        if (thumbnail != null) {

                            Image(
                                bitmap = thumbnail.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                        } else {

                            // fallback if frame fails
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
            }



        }

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
                profileImageUrl = post.profile_image,
                isLiked = post.user_reaction == "like",
                onLiked = { onPostLike(post) },
                onFollow = {},
                onPostProfileClick = {},
                onSaved = { onPostSave(post) },
                isSaved = post.is_saved,
                createdAt = post.created_at
            )
        }
    }
    selectedReelIndex?.let { startIndex ->

        Dialog(
            onDismissRequest = { selectedReelIndex = null }
        ) {

            ReelsScreen(
                reels = reels,
                loading = false,
                startIndex = startIndex,   // 🔥 NEW PARAM
                onLike = onReelLike,
                onSave = onReelSave
            )
        }
    }





}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}