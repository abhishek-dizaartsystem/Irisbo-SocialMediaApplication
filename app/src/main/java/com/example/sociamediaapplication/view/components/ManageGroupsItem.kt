package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Delete
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.screens.AllGroupMemberItem
import com.example.sociamediaapplication.view.screens.GroupMemberRequestItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageGroupsItem(
    isPublic: Boolean = true,
    groupId: String = "1",
    onPrivacyToggle: ()-> Unit = {},
    isPostApproval: Boolean = true,
    onPostApprovalToggle: ()-> Unit = {},
    onDelete: ()-> Unit = {},
    onGroupClick:(String) -> Unit = {},
    onEditClick: (String) -> Unit = {}
) {

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var sheetType by remember { mutableStateOf("join") }
    var isPendingRequests by remember { mutableStateOf(true) }

    var showDropDownMenu by remember { mutableStateOf(false) }


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            when (sheetType) {
                "requests" -> {
                    Text(
                        text = "Group Requests",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                "manage members" -> {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Members of GroupID-$groupId",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = LGrey,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Button(
                                onClick = {
                                    isPendingRequests = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(!isPendingRequests) LGrey else White
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(0.5f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Pending",
                                    color = if(!isPendingRequests) GreyTxt else Black,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    isPendingRequests = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(isPendingRequests) LGrey else White
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "All",
                                    color = if(isPendingRequests) GreyTxt else Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        if(isPendingRequests){
                            LazyColumn (
                                Modifier
                                    .padding(vertical = 12.dp, horizontal = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                items(3){
                                    GroupMemberRequestItem()
                                }
                            }
                        }
                        else{
                            LazyColumn (
                                Modifier
                                    .padding(vertical = 12.dp, horizontal = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                items(5){
                                    AllGroupMemberItem()
                                }
                            }
                        }

                    }
                }

                "join" -> {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Group Settings",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(
                                        text = "Private Group",
                                        color = Black,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "Only followers can see post",
                                        color = GreyTxt,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Switch(
                                checked = isPublic,
                                onCheckedChange = {
                                    onPrivacyToggle()
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(
                                        text = "Post Approval",
                                        color = Black,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "Members can create post",
                                        color = GreyTxt,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Switch(
                                checked = isPostApproval,
                                onCheckedChange = {
                                    onPostApprovalToggle()
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }
                        Button(
                            onClick = onDelete,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Red
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.delete_svgrepo_com),
                                contentDescription = "",
                                Modifier.size(20.dp)
                            )
                            Text(
                                text = " Delete Group",
                                color = White,
                                fontSize = 18.sp
                            )
                        }
                    }

                }
            }
        }
    }

    Box(
        Modifier
            .background(White)
            .clip(RoundedCornerShape(12.dp))
    ){
        Image(
            painter = painterResource(R.drawable.travel),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .aspectRatio(1.2f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {}
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.travel),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "React Developers ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        painter = painterResource(
                                            if(isPublic) R.drawable.global_svgrepo_com
                                            else R.drawable.lock_svgrepo_com
                                        ),
                                        contentDescription = "",
                                        Modifier.size(20.dp),
                                        tint = GreyTxt
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    Text(
                                        "28K followers",
                                        color = GreyTxt,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        "Technology",
                                        color = GreyTxt,
                                        fontSize = 14.sp
                                    )

                                }


                            }
                        }

                        Column() {
                            IconButton(
                                onClick = {
                                    showDropDownMenu = true
                                },
                                Modifier.size(30.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.menu_dots_svgrepo_com),
                                    contentDescription = "",
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
                                            "View Group",
                                            fontSize = 16.sp)
                                    },
                                    onClick = {
                                        onGroupClick(groupId)
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.eye_outlined_svgrepo_com),
                                            contentDescription = "",
                                            Modifier.size(24.dp)
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Group Settings",
                                            fontSize = 16.sp)
                                    },
                                    onClick = {
                                        sheetType = "join"
                                        showSheet = true
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.settings_logo),
                                            contentDescription = "",
                                            Modifier.size(20.dp)
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Manage Members",
                                            fontSize = 16.sp)
                                    },
                                    onClick = {
                                        sheetType = "manage members"
                                        showSheet = true
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.team_3),
                                            contentDescription = "",
                                            Modifier.size(24.dp)
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Edit",
                                            fontSize = 16.sp)
                                    },
                                    onClick = {
                                        onEditClick(groupId)
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.edit_1_svgrepo_com),
                                            contentDescription = "",
                                            Modifier.size(24.dp)
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Mute",
                                            fontSize = 16.sp)
                                    },
                                    onClick = {},
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.notification_slient_svgrepo_com),
                                            contentDescription = "",
                                            Modifier.size(24.dp)
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Delete Group",
                                            fontSize = 16.sp,
                                            color = Red)
                                    },
                                    onClick = {},
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.delete_svgrepo_com),
                                            contentDescription = "",
                                            Modifier.size(24.dp),
                                            tint = Red
                                        )
                                    }
                                )
                            }
                        }


                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                sheetType = "requests"
                                showSheet = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BackgroundColor
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            border = BorderStroke(1.dp, Grey)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.add_friend_24),
                                contentDescription = "",
                                modifier = Modifier.size(16.dp),
                                tint = Black
                            )
                            Text(
                                "  Requests",
                                color = Black
                            )
                        }
                        Button(
                            onClick = {
                                sheetType = "join"
                                showSheet = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BackgroundColor
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            border = BorderStroke(1.dp, color = Grey)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.settings_logo),
                                contentDescription = "",
                                modifier = Modifier.size(16.dp),
                                tint = Black
                            )
                            Text(
                                "  Settings",
                                color = Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageGroupsItemPreview(){
    ManageGroupsItem(
        onPrivacyToggle = {}
    )
}