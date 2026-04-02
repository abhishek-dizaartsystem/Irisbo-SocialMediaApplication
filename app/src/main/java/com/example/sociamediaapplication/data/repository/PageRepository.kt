package com.example.sociamediaapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.uriToFile
import com.example.sociamediaapplication.model.response.PageCategoriesResponse
import com.example.sociamediaapplication.model.response.PageFollowersResponse
import com.example.sociamediaapplication.model.response.PagePostsResponse
import com.example.sociamediaapplication.model.response.PagesResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PageRepository(
    private val tokenManager: TokenManager
) {
    private val api = RetrofitClient.pageApi

    suspend fun fetchPages(): PagesResponse{
        val token = "Bearer ${tokenManager.getToken()}"
        val response = api.fetchPages(token)

        return response
    }

    suspend fun fetchPageFollowers(pageId: Int): PageFollowersResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchPageFollowers(token, pageId)
    }

    suspend fun fetchPagePosts(pageId: Int): PagePostsResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchPagePosts(token, pageId)
    }

    suspend fun createPage(
        name: String,
        bio: String,
        profileImageUri: Uri,
        coverImageUri: Uri,
        website: String,
        phone: String,
        email: String,
        address: String,
        context: Context,
        category_id: Int
    ) {
        val token = "Bearer ${tokenManager.getToken()}"

        val nameBody = name.toRequestBody("text/plain".toMediaType())
        val bioBody = bio.toRequestBody("text/plain".toMediaType())
        val websiteBody = website.toRequestBody("text/plain".toMediaType())
        val phoneBody = phone.toRequestBody("text/plain".toMediaType())
        val emailBody = email.toRequestBody("text/plain".toMediaType())
        val addressBody = address.toRequestBody("text/plain".toMediaType())
        val categoryIdBody = category_id.toString().toRequestBody("text/plain".toMediaType())

        val profileFile = uriToFile(profileImageUri, context)
        val profileMime = context.contentResolver.getType(profileImageUri) ?: "image/*"
        val profilePart = MultipartBody.Part.createFormData(
            "profile_image",
            profileFile.name,
            profileFile.asRequestBody(profileMime.toMediaTypeOrNull())
        )

        val coverFile = uriToFile(coverImageUri, context)
        val coverMime = context.contentResolver.getType(coverImageUri) ?: "image/*"
        val coverPart = MultipartBody.Part.createFormData(
            "cover_image",
            coverFile.name,
            coverFile.asRequestBody(coverMime.toMediaTypeOrNull())
        )

        api.createPage(
            token,
            profilePart,
            coverPart,
            nameBody,
            bioBody,
            websiteBody,
            phoneBody,
            emailBody,
            addressBody,
            categoryIdBody
        )
    }

    suspend fun deletePage(pageId: Int) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.deletePage(token, pageId)
    }

    suspend fun followPage(pageId: Int) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.followPage(token, pageId)
    }

    suspend fun unfollowPage(pageId: Int) {
        val token = "Bearer ${tokenManager.getToken()}"
        api.unfollowPage(token, pageId)
    }

    suspend fun fetchCategoriesType(): PageCategoriesResponse {
        val token = "Bearer ${tokenManager.getToken()}"
        return api.fetchCategories(token)
    }
}