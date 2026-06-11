package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.model.response.VideoComment
import com.example.sociamediaapplication.ui.theme.GreyTxt

@Composable
fun CommentSection(
    comments: List<VideoComment>,
    onAddComment: (content: String, parentId: Int?) -> Unit,
    onLikeComment: (commentId: Int) -> Unit,
    onDislikeComment: (commentId: Int) -> Unit
) {
    var commentText by remember { mutableStateOf("") }
    var replyingTo by remember { mutableStateOf<String?>(null) }
    var replyingToCommentId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Comment input and reply bar
        if (replyingTo != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Replying to @$replyingTo",
                    color = GreyTxt,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Cancel",
                    color = GreyTxt,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        replyingTo = null
                        replyingToCommentId = null
                    }
                )
            }
        }

        CustomTextField(
            label = "",
            value = commentText,
            placeHolder = if (replyingTo == null) "Comment your views..." else "Write a reply...",
            onValueChange = { commentText = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (commentText.isBlank()) return@Button
                onAddComment(commentText, replyingToCommentId)
                commentText = ""
                replyingTo = null
                replyingToCommentId = null
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = if (replyingTo == null) "Comment" else "Reply")
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(comments) { comment ->
                CommentThread(
                    comment = comment,
                    onLiked = onLikeComment,
                    onDisliked = onDislikeComment,
                    onReply = { selectedComment ->
                        replyingTo = selectedComment.username
                        replyingToCommentId = selectedComment.id
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}