package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.request.AddCommentRequest
import com.example.sociamediaapplication.model.request.PostReactionRequest
import com.example.sociamediaapplication.model.response.BasicResponse
import com.example.sociamediaapplication.model.response.BasicResponse2
import com.example.sociamediaapplication.model.response.GlobalPostsResponse
import com.example.sociamediaapplication.model.response.GroupPostDetailsResponse
import com.example.sociamediaapplication.model.response.LikePostResponse
import com.example.sociamediaapplication.model.response.LikeResponse
import com.example.sociamediaapplication.model.response.PostResponse
import com.example.sociamediaapplication.model.response.PostsListResponse
import com.example.sociamediaapplication.model.response.SaveResponse
import com.example.sociamediaapplication.model.response.VideoReactionRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

import com.example.sociamediaapplication.model.response.VideoCommentsResponse

interface PostApi {

    //NEW APIS
    @POST("api/savePosts/toggle/{id}")
    suspend fun toggleSavePost(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SaveResponse

    @POST("api/posts/{id}/save")
    suspend fun savePost(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): BasicResponse2

    @DELETE("api/posts/{id}/save")
    suspend fun unsavePost(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): BasicResponse2

    @POST("api/posts/{id}/react")
    suspend fun toggleLikePost(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body request: PostReactionRequest
    ): LikePostResponse

    @GET("api/posts/{postId}/comments")
    suspend fun getComments(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): VideoCommentsResponse

    @POST("api/posts/{postId}/comments")
    suspend fun createComment(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int,
        @Body request: AddCommentRequest
    )

    @POST("api/comments/{commentId}/react")
    suspend fun reactToComment(
        @Header("Authorization") token: String,
        @Path("commentId") commentId: Int,
        @Body request: VideoReactionRequest
    )

    @DELETE("api/comments/{commentId}/react")
    suspend fun removeCommentReaction(
        @Header("Authorization") token: String,
        @Path("commentId") videoId: Int
    )










    //OLD APIS

    @Multipart
    @POST("api/groups/{group_id}/posts")
    suspend fun createGroupPost(
        @Header("Authorization") token: String,
        @Path("group_id") groupId: Int,
        @Part("caption") caption: RequestBody,
        @Part media: List<MultipartBody.Part> = emptyList()
    )

    @GET("api/groups/posts/{post_id}")
    suspend fun getGroupPostDetails(
        @Header("Authorization") token: String,
        @Path("post_id") postId: Int
    ): GroupPostDetailsResponse

    @GET("api/users/{userId}/posts")
    suspend fun getAllPosts(
        @Header("Authorization") token: String,
        @Path("userId") id: Int
    ): List<PostResponse>

    @GET("api/posts/all")
    suspend fun getAllPostsGloabal(
        @Header("Authorization") token: String
    ): GlobalPostsResponse

    @Multipart
    @POST("api/posts/create")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Part("caption") caption: RequestBody,
        @Part media: List<MultipartBody.Part>? = emptyList()
    ): BasicResponse

//    @POST("api/likePosts/{id}/like")
//    suspend fun toggleLikePost(
//        @Path("id") id: Int,
//        @Header("Authorization") token: String
//    ): LikeResponse

//    @POST("api/savePosts/toggle/{id}")
//    suspend fun toggleSavePost(
//        @Path("id") id: Int,
//        @Header("Authorization") token: String
//    ): SaveResponse
}
