package com.example.sociamediaapplication.data.remote

import androidx.room.Delete
import com.example.sociamediaapplication.model.response.AddGroupResponse
import com.example.sociamediaapplication.model.response.GroupCategoryTypesResponse
import com.example.sociamediaapplication.model.response.GroupDetailsResponse
import com.example.sociamediaapplication.model.response.GroupMembersResponse
import com.example.sociamediaapplication.model.response.GroupsResponse
import com.example.sociamediaapplication.model.response.MyGroupsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface GroupApi {

    @GET("api/groups")
    suspend fun getGroups(
        @Header("Authorization") token: String
    ): GroupsResponse

    @GET("api/groups/my")
    suspend fun getMyGroups(
        @Header("Authorization") token: String
    ): MyGroupsResponse

    @GET("api/groups/{group_id}")
    suspend fun getGroupDetails(
        @Header("Authorization") token: String,
        @Path("group_id") group_id: Int
    ): GroupDetailsResponse

    @GET("api/groups/{groupId}/members")
    suspend fun getGroupMembers(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Int
    ): GroupMembersResponse

    @PUT("api/groups/{groupId}/approve/{userId}")
    suspend fun approveMemberRequest(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Int,
        @Path("userId") userId: Int
    )

    @DELETE("api/groups/{groupId}/members/{userId}/reject")
    suspend fun rejectMemberRequest(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Int,
        @Path("userId") userId: Int
    )

    @GET("api/groups/categories")
    suspend fun getCategories(): GroupCategoryTypesResponse

    @DELETE("api/groups/{groupId}")
    suspend fun deleteGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Int
    )

    @POST("api/groups/{groupId}/leave")
    suspend fun leaveGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Int
    )

    @POST("api/groups/{groupId}/join")
    suspend fun joinGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Int
    )


    @Multipart
    @POST("api/groups/add")
    suspend fun addGroup(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("privacy") privacy: RequestBody,
        @Part("approval_required") approval_required: RequestBody,
        @Part("only_admin_post") only_admin_post: RequestBody,
        @Part("category_id") category: RequestBody
    ): AddGroupResponse
}