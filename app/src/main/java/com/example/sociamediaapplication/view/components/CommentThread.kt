package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.data.utils.formatToPostTime
import com.example.sociamediaapplication.model.response.VideoComment
import com.example.sociamediaapplication.ui.theme.GreyTxt

@Composable
fun CommentThread(
    comment: VideoComment,
    onLiked: (Int) -> Unit,
    onDisliked: (Int) -> Unit,
    onReply: (VideoComment) -> Unit,
    depth: Int = 0
) {

    var showReplies by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(
            start = (depth * 24).dp
        )
    ) {

        CommentItem(
            userName = "@${comment.username}",
            comment = comment.content,
            totalLikes = comment.likes,
            totalDislikes = comment.dislikes,
            totalReplies = comment.replies.size,
            isLiked = comment.user_reaction == "like",
            isDisliked = comment.user_reaction == "dislike",
            profileImage = comment.profile_image,
            commentTime = formatToPostTime(
                comment.created_at
            ),

            onLiked = {
                onLiked(comment.id)
            },

            onDisliked = {
                onDisliked(comment.id)
            },

            onReplyClicked = {
                onReply(comment)
            },

            onUserProfileClick = {}
        )

        // 👇 ADD THIS RIGHT BELOW CommentItem
        if (comment.replies.isNotEmpty()) {

            Text(
                text =
                    if (showReplies)
                        "Hide Replies"
                    else
                        "View ${comment.replies.size} Replies",

                modifier = Modifier
                    .padding(start = 56.dp)
                    .clickable {
                        showReplies = !showReplies
                    },

                color = GreyTxt
            )
        }

        // 👇 REPLIES RENDER HERE
        if (showReplies) {

            comment.replies.forEach { reply ->

                CommentThread(
                    comment = reply,
                    onLiked = onLiked,
                    onDisliked = onDisliked,
                    onReply = onReply,
                    depth = depth + 1
                )
            }
        }

    }
}