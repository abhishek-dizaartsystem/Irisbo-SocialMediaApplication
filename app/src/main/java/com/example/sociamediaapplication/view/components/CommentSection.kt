package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.CommentModel
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun CommentSection(
    navController: NavController = rememberNavController()
) {

    var comment by remember { mutableStateOf("") }

    val commentList = remember { mutableStateListOf(
        CommentModel("1", 20, 2,3, false, false),
        CommentModel("2", 30, 4, 4,false, false),
        CommentModel("3", 10, 6, 1,false, false),
        CommentModel("4", 24, 2,3, false, false),
        CommentModel("5", 22, 7, 2, false, false),
        CommentModel("6", 2, 9, 8, false, false),
        CommentModel("7", 1, 10,2, false, false)
    ) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row() {
            IconButton(
                onClick = { },
                modifier = Modifier.size(48.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Red
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle_5),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }
            TextField(
                value = comment,
                onValueChange = {newValue->
                    comment = newValue
                },
                placeholder = {
                    Text(
                        text = "Comment your views...",
                        color = GreyTxt
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    unfocusedContainerColor = LGrey,
                    focusedContainerColor = LGrey
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            //comment
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.send_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp)
                                .rotate(15f)
                        )
                    }
                }
            )


        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()

        LazyColumn() {
            items(commentList.size){index->
                val comment = commentList[index]
                CommentItem(
                    userName = "@coolboy",
                    comment = "This is amazing",
                    totalLikes = comment.likes,
                    totalDislikes = comment.dislikes,
                    totalReplies = comment.replies,
                    isLiked = comment.isLiked,
                    isDisliked = comment.isDisliked,
                    onLiked = {
                        if(comment.isLiked){
                            commentList[index] = comment.copy(
                                isLiked = false,
                                likes = comment.likes-1
                            )
                        }else{
                            commentList[index] = comment.copy(
                                isLiked = true,
                                likes = comment.likes+1,
                                isDisliked = false
                            )
                        }
                    },
                    onDisliked = {
                        if(comment.isDisliked){
                            commentList[index] = comment.copy(
                                isDisliked = false,
                                dislikes = comment.dislikes-1
                            )
                        }else{
                            commentList[index] = comment.copy(
                                isDisliked = true,
                                dislikes = comment.likes+1,
                                isLiked = false
                            )
                        }
                    },
                    onReplyClicked = {},
                    onUserProfileClick = {

                    }
                )
                HorizontalDivider()
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CommentSectionPreview(){
    CommentSection()
}