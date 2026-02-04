package com.example.sociamediaapplication.view.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun SendSection() {

    var searchTxt by remember { mutableStateOf("") }

    val friendsList = remember {
        listOf(
            "Abhinav",
            "Kartik",
            "Rohit",
            "Aman",
            "Nitin",
            "Vivek",
            "x",
            "y",
            "z"
        )
    }

    val selectedFriends = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        TextField(
            value = searchTxt,
            onValueChange = {newMessage->
                searchTxt = newMessage
            },
            placeholder = {
                Text(
                    text = "Search friends...",
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
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    tint = GreyTxt
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(friendsList){friend->
                val isSelected = selectedFriends.contains(friend)
                SendFriendsItem(
                    name = friend,
                    isSelected = isSelected,
                    onClick = {
                        if(isSelected){
                            selectedFriends.remove(friend)
                        }else{
                            selectedFriends.add(friend)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if(selectedFriends.isNotEmpty()){
            Button(
                onClick = {
                    //send
                },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Send")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SendSectionPreview(){
    SendSection()
}