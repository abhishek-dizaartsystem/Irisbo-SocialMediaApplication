package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.repository.GroupRepository
import com.example.sociamediaapplication.data.utils.toPlainRequestBody
import com.example.sociamediaapplication.model.Group
import com.example.sociamediaapplication.model.response.GroupCategoryTypesResponse
import com.example.sociamediaapplication.model.response.GroupDetailsResponse
import com.example.sociamediaapplication.model.response.GroupMembersResponse
import com.example.sociamediaapplication.model.response.GroupsResponse
import com.example.sociamediaapplication.model.response.MyGroupsResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class GroupViewModel(
    private val repository: GroupRepository
): ViewModel() {

    private val _coverPhoto = MutableStateFlow<Any>(R.drawable.coverphoto)
    val coverPhoto: StateFlow<Any> = _coverPhoto

    private val _groupProfile = MutableStateFlow<Any>(R.drawable.photoinitial)
    val groupProfile: StateFlow<Any> = _groupProfile

    private val _discoverGroups = MutableStateFlow<GroupsResponse?>(null)
    val discoverGroups: StateFlow<GroupsResponse?> = _discoverGroups

    private val _myGroups = MutableStateFlow<MyGroupsResponse?>(null)
    val myGroups: StateFlow<MyGroupsResponse?> = _myGroups

    private val _groupDetails = MutableStateFlow<GroupDetailsResponse?>(null)
    val groupDetails: StateFlow<GroupDetailsResponse?> = _groupDetails

    private val _groupMembers = MutableStateFlow<GroupMembersResponse?>(null)
    val groupMembers: StateFlow<GroupMembersResponse?> = _groupMembers

    private val _groupCategoryTypes = MutableStateFlow<GroupCategoryTypesResponse?>(null)
    val groupCategoryTypes: StateFlow<GroupCategoryTypesResponse?> = _groupCategoryTypes


    fun loadGroups(){
        viewModelScope.launch {
            try {
                _discoverGroups.value = repository.getGroups()
            }catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)
            }
        }
    }

    fun loadMyGroups(){
        viewModelScope.launch {
            try {
                _myGroups.value = repository.getMyGroups()
            }catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)
            }
        }

    }

    fun loadGroupDetails(
        group_id: Int
    ){
        viewModelScope.launch {
            try{
                _groupDetails.value = repository.getGroupDetails(group_id)
            }catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)
            }
        }

    }

    fun loadGroupMembers(
        groupId: Int
    ){
        viewModelScope.launch {
            try{
                _groupMembers.value = repository.getGroupMembers(groupId)
            } catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)
            }
        }

    }

    fun approveMemberRequest(
        groupId: Int,
        userId: Int
    ){
        viewModelScope.launch{
            try{
                repository.approveMemberRequest(groupId, userId)
            }catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)
            }
        }
    }

    fun rejectMemberRequest(
        groupId: Int,
        userId: Int
    ){
        viewModelScope.launch{
            try{
                repository.rejectMemberRequest(groupId, userId)
            }catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)
            }
        }
    }


    fun loadGroupCategoryTypes(){
        viewModelScope.launch {
            try {
                _groupCategoryTypes.value = repository.getGroupCategoryTypes()
            }catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)
            }
        }
    }

//    fun togglePostApproval(groupId: Int){
//        _groups.update {groupsList->
//            groupsList.map {group->
//                if(group.id == groupId){
//                    group.copy(
//                        isPostApproval = !group.isPostApproval
//                    )
//                }
//                else{
//                    group
//                }
//            }
//        }
//    }



    fun updateCoverImage(uri: Uri){
        _coverPhoto.value = uri
    }

    fun updateGroupProfile(uri: Uri){
        _groupProfile.value = uri
    }

    fun deleteGroup(groupId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteGroup(groupId)
            } catch (e: Exception) {
                Log.e("GroupViewModel", e.message, e)
            }
        }
    }

    fun joinGroup(groupId: Int){
        viewModelScope.launch {
            try {
                repository.joinGroup(groupId)
            }catch (e: Exception){
                Log.e("GroupViewModel", e.message, e)

            }
        }
    }

    fun leaveGroup(groupId: Int) {
        viewModelScope.launch {
            try {
                repository.leaveGroup(groupId)
            } catch (e: Exception) {
                Log.e("GroupViewModel", e.message, e)

            }
        }
    }

    fun addGroup(
        context: Context,
        imageUri: Uri,
        name: String,
        description: String,
        privacy: String,
        approvalRequired: String,
        onlyAdminPost: String,
        category: Int
    ) {
        viewModelScope.launch {
            try {

                Log.d("CREATE_DEBUG", "IMAGE URI = $imageUri")
                Log.d("CREATE_DEBUG", "NAME = $name")
                Log.d("CREATE_DEBUG", "DESCRIPTION = $description")
                Log.d("CREATE_DEBUG", "PRIVACY = $privacy")
                Log.d("CREATE_DEBUG", "APPROVAL REQUIRED = $approvalRequired")
                Log.d("CREATE_DEBUG", "ONLY ADMIN POST = $onlyAdminPost")
                Log.d("CREATE_DEBUG", "CATEGORY = $category")


                repository.addGroup(
                    context = context,
                    imageUri = imageUri,
                    name = name.toPlainRequestBody(),
                    description = description.toPlainRequestBody(),
                    privacy = privacy.toPlainRequestBody(),
                    approvalRequired = approvalRequired.toInt().toPlainRequestBody(),
                    onlyAdminPost = onlyAdminPost.toInt().toPlainRequestBody(),
                    category = category.toString().toPlainRequestBody()
                )


            } catch (e: HttpException) {
                Log.e("CREATE_ERROR", e.response()?.errorBody()?.string() ?: "Unknown error")
            }
        }
    }
}