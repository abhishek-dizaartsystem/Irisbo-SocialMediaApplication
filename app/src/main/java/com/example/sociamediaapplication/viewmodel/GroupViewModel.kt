package com.example.sociamediaapplication.viewmodel

import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GroupViewModel: ViewModel() {

    private val _coverPhoto = MutableStateFlow<Any>(R.drawable.coverphoto)
    val coverPhoto: StateFlow<Any> = _coverPhoto

    private val _groupProfile = MutableStateFlow<Any>(R.drawable.photoinitial)
    val groupProfile: StateFlow<Any> = _groupProfile

    private val _groups = MutableStateFlow<List<Group>>(
        listOf(
            Group(
                1, "Travel Group",
                followers = "29K followers",
                category = "Travel",
                pendingRequests = 2,
                isPostApproval = false,
                isPublic = false
            ),
            Group(
                2, "React Developers",
                followers = "23K followers",
                category = "Technology",
                pendingRequests = 3,
                isPostApproval = true,
                isPublic = false
            )
        )
    )
    val groups: StateFlow<List<Group>> = _groups

    fun togglePostApproval(groupId: Int){
        _groups.update {groupsList->
            groupsList.map {group->
                if(group.id == groupId){
                    group.copy(
                        isPostApproval = !group.isPostApproval
                    )
                }
                else{
                    group
                }
            }
        }
    }

    fun toggleMyGroupPrivacy(groupId: Int){
        _groups.update {groupList->
            groupList.map {group->
                if(group.id == groupId){
                    group.copy(
                        isPublic = !group.isPublic
                    )
                }
                else{
                    group
                }
            }
        }
    }

    fun updateCoverImage(uri: Uri){
        _coverPhoto.value = uri
    }

    fun updateGroupProfile(uri: Uri){
        _groupProfile.value = uri
    }
}