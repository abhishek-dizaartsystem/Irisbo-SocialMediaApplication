package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.model.FeedPost
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.PostRepository
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.factory.PostViewModelFactory
import com.example.sociamediaapplication.view.components.Post

@Composable
fun HomeScreen1() {

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }
    val postRepository = remember { PostRepository(tokenManager) }
    val postFactory = remember { PostViewModelFactory(postRepository) }
    val postViewModel: PostViewModel = viewModel(factory = postFactory)

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

            LaunchedEffect(post) {
                post.media = post.media?.map { mediaItem->
                    correctUrl(mediaItem)
                }
            }
            Post(
                uName = post.username ?: "",
                caption = post.caption ?: "",
                mediaList = post.media,
                postLikes = post.likes_count ?: 0,
                profileImageUrl = post.profile_image,
                isLiked = post.user_reaction == "like",
                onLiked = { postViewModel.toggleGlobalLike(post) },
                onFollow = {},
                onPostProfileClick = {},
                onSaved = { postViewModel.toggleGlobalSave(post) },
                isSaved = post.is_saved,
                createdAt = post.created_at
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen1Preview(){
    HomeScreen1()
}
