package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Gold
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun AddReviewSection(
    onSendReview: (String, Int)-> Unit = {review, rating->}
){
    var review by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(5) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(

            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Spacer(Modifier.width(60.dp))

            repeat(5) { index ->

                val starNumber = index + 1

                IconButton(
                    onClick = {
                        rating = starNumber
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star_svgrepo_com),
                        contentDescription = null,
                        tint = if (starNumber <= rating)
                            Gold
                        else
                            GreyTxt,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                value = review,
                onValueChange = {newValue->
                    review = newValue
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
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .heightIn(50.dp, 300.dp)
                    .padding(horizontal = 12.dp)
                    .weight(1f)
            )
            IconButton(
                onClick = {
                    onSendReview(review, rating)
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
    }

}

@Preview(showBackground = true)
@Composable
fun AddReviewSectionPreview(){
    AddReviewSection()
}