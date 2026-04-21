package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.FriendStoriesResponse
import com.example.sociamediaapplication.model.response.MyStoriesResponse
import com.example.sociamediaapplication.model.response.SingleUserStoryResponse
import com.example.sociamediaapplication.model.response.StoryViewerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

class StoryRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.storyApi

    suspend fun getFriendsStories(): FriendStoriesResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getFriendsStories(token)
    }

    suspend fun createStory(context: Context, imageUri: Uri) {
        val token = "Bearer ${tokenManager.getToken()}"

        val contentResolver = context.contentResolver

        val inputStream = contentResolver.openInputStream(imageUri)
            ?: throw Exception("Unable to open image URI")

        val fileBytes = inputStream.readBytes()
        withContext(Dispatchers.IO) {
            inputStream.close()
        }

        val requestBody = fileBytes.toRequestBody("image/png".toMediaTypeOrNull())

        val part = MultipartBody.Part.createFormData(
            "media",
            "story_${System.currentTimeMillis()}.png",
            requestBody
        )

        api.createStory(token, part)
    }

    suspend fun getMyStories(): MyStoriesResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getMyStories(token)
    }

    suspend fun getSingleUserStories(userId: Int): SingleUserStoryResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getSingleUserStories(token, userId)
    }



    suspend fun markStoryViewed(storyId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        api.markStoryViewed(token, storyId)
    }

    suspend fun getStoryViewers(storyId: Int): StoryViewerResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getStoryViewers(token, storyId)
    }

    suspend fun deleteStory(storyId: Int){
        val token = "Bearer ${tokenManager.getToken()}"

        api.deleteStory(token, storyId)
    }
}