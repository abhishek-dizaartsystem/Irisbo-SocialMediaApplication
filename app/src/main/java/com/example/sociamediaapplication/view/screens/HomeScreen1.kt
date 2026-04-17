package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.view.components.Post

@Composable
fun HomeScreen1(
    postViewModel: PostViewModel,
    onOtherProfileClick: (Int) -> Unit = {}
) {

    val posts by postViewModel.globalPosts.collectAsState()

    LaunchedEffect(Unit) {
        postViewModel.loadGlobalPosts()
    }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {


        items(posts.size) {index->
            var post = posts[index]

            Post(
                uName = post.username ?: "",
                caption = post.caption ?: "",
                mediaList = post.media,
                postLikes = post.likes_count ?: 0,
                profileImageUrl = correctUrl(post.profile_image),
                isLiked = post.user_reaction == "like",
                onLiked = { postViewModel.toggleGlobalLike(post) },
                onFollow = {},
                onSaved = { postViewModel.toggleGlobalSave(post) },
                isSaved = post.is_saved,
                createdAt = post.created_at,
                onOtherProfileClick = {
                    onOtherProfileClick(post.user_id)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


