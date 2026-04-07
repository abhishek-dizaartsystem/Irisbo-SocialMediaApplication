package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.response.AddGroupResponse
import com.example.sociamediaapplication.model.response.GroupCategoryTypesResponse
import com.example.sociamediaapplication.model.response.GroupDetailsResponse
import com.example.sociamediaapplication.model.response.GroupMembersResponse
import com.example.sociamediaapplication.model.response.GroupPostDetailsResponse
import com.example.sociamediaapplication.model.response.GroupPostsResponse
import com.example.sociamediaapplication.model.response.GroupsResponse
import com.example.sociamediaapplication.model.response.MyGroupsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

class GroupRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.groupApi

    suspend fun getGroups(): GroupsResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        Log.d("TOKEN_DEBUG", "Token = $token")
        return api.getGroups(token)
    }

    suspend fun getMyGroups(): MyGroupsResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMyGroups(token)
    }

    suspend fun getGroupDetails(
        group_id: Int
    ): GroupDetailsResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getGroupDetails(token, group_id)
    }

    suspend fun updateGroupDetails(
        groupId: Int,
        context: Context,
        imageUri: Uri,
        name: RequestBody,
        description: RequestBody,
        privacy: RequestBody,
        approvalRequired: RequestBody,
        onlyAdminPost: RequestBody,
        category: RequestBody
    ){
        val token = tokenManager.getToken()

        val file = uriToFile(imageUri, context)

        val mime = context.contentResolver.getType(imageUri) ?: "image/jpeg"

        val requestFile = file.asRequestBody(
            mime.toMediaTypeOrNull()
        )

        val imagePart = MultipartBody.Part.createFormData(
            "cover_image",
            file.name,
            requestFile
        )

        api.updateGroup(
            token = "Bearer $token",
            groupId = groupId,
            image = imagePart,
            name = name,
            description = description,
            privacy = privacy,
            approval_required = approvalRequired,
            only_admin_post = onlyAdminPost,
            category = category
        )
    }

    suspend fun getGroupMembers(
        groupId: Int
    ): GroupMembersResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getGroupMembers(token, groupId)
    }

    suspend fun approveMemberRequest(
        groupId: Int,
        userId: Int
    ){
        val token = "Bearer ${tokenManager.getToken()}"

        Log.d("ApproveMember_DEBUG", "groupId = $groupId\nuserId = $userId")

        api.approveMemberRequest(token, groupId, userId)
    }

    suspend fun rejectMemberRequest(
        groupId: Int,
        userId: Int
    ){
        val token = "Bearer ${tokenManager.getToken()}"
        api.rejectMemberRequest(token, groupId, userId)
    }

    suspend fun getGroupCategoryTypes(): GroupCategoryTypesResponse {
        return api.getCategories()
    }

    suspend fun deleteGroup(
        groupId: Int
    ) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.deleteGroup(token, groupId)
    }

    suspend fun leaveGroup(
        groupId: Int
    ) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.leaveGroup(token, groupId)
    }

    suspend fun joinGroup(
        groupId: Int
    ){
        val token = "Bearer ${tokenManager.getToken()}"
        api.joinGroup(token, groupId)
    }

    suspend fun removeMember(
        groupId: Int,
        userId: Int
    ){
        val token = "Bearer ${tokenManager.getToken()}"
        api.removeMember(token, groupId, userId)
    }



    suspend fun addGroup(
        context: Context,
        imageUri: Uri,
        name: RequestBody,
        description: RequestBody,
        privacy: RequestBody,
        approvalRequired: RequestBody,
        onlyAdminPost: RequestBody,
        category: RequestBody
    ): AddGroupResponse = withContext(Dispatchers.IO) {

        val token = tokenManager.getToken()

        val file = uriToFile(imageUri, context)

        val mime = context.contentResolver.getType(imageUri) ?: "image/jpeg"

        val requestFile = file.asRequestBody(
            mime.toMediaTypeOrNull()
        )

        val imagePart = MultipartBody.Part.createFormData(
            "cover_image",
            file.name,
            requestFile
        )

        val response = api.addGroup(
            token = "Bearer $token",
            image = imagePart,
            name = name,
            description = description,
            privacy = privacy,
            approval_required = approvalRequired,
            only_admin_post = onlyAdminPost,
            category = category
        )
        Log.d("UPLOAD_DEBUG", "SUCCESS RESPONSE = $response")
        return@withContext response
    }

    suspend fun getGroupPosts(
        groupId: Int,
        page: Int,
        limit: Int
    ): GroupPostsResponse{
        val token = "Bearer ${tokenManager.getToken()}"
        val response = api.getGroupPosts(token, groupId, page, limit)

        return response
    }

    suspend fun getGroupPostDetails(
        postId: Int
    ): GroupPostDetailsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getGroupPostDetails(token, postId)
    }

    suspend fun deleteGroupPost(
        postId: Int
    ){
        val token = "Bearer ${tokenManager.getToken()}"

        api.deleteGroupPost(token, postId)
    }

}