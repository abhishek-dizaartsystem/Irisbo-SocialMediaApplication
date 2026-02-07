package com.example.sociamediaapplication.model

data class Group(
    val id:Int,
    val name: String = "Travel Group",
    val followers: String = "28K followers",
    val category: String = "Technology",
    val pendingRequests: Int = 3,
    val isPostApproval: Boolean = true,
    val isPublic: Boolean,
)
