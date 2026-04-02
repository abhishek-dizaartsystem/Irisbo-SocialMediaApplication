package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.White

@Composable
fun AllGroupMemberItem(
    painter: Painter = painterResource(R.drawable.rectangle_36__2_),
    name: String = "Arjun",
    joinedOn: String = "Jan 2023",
    memberType: String = "Member",
    username: String = "arjun",
    image: String = "https://picsum.photos/200",
    role: String = "admin",
    uid: Int = 1,
    onRemoveMember: () -> Unit = {}
){


    var showDropDownMenu by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd
                ){
                    AsyncImage(
                        model = image,
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = username,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Column(
                            Modifier
                                .background(LLBlue, RoundedCornerShape(12.dp))
                        ) {
                            Text(
                                text = role,
                                color = Blue,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                    Text(
                        text = joinedOn,
                        fontSize = 14.sp,
                        color = GreyTxt
                    )
                }
            }
            Column () {
                IconButton(
                    onClick = {
                        showDropDownMenu = true
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                        contentDescription = null,
                        Modifier.size(20.dp)
                    )
                }
                DropdownMenu(
                    expanded = showDropDownMenu,
                    onDismissRequest = {
                        showDropDownMenu = false
                    },
                    shape = RoundedCornerShape(16.dp),
                    containerColor = White
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Make Admin",
                                fontSize = 16.sp)
                        },
                        onClick = {
                            showDropDownMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.security_2_svgrepo_com),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Remove from group",
                                fontSize = 16.sp)
                        },
                        onClick = {
                            showDropDownMenu = false

                            onRemoveMember()
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.logout_svgrepo_com),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllGroupMemberItemPreview(){
    AllGroupMemberItem()
}