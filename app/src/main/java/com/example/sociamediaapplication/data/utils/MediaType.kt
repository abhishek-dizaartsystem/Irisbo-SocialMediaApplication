package com.example.sociamediaapplication.data.utils

fun isVideo(url: String): Boolean {
    val lower = url.lowercase()
    return lower.endsWith(".mp4") ||
            lower.endsWith(".mov") ||
            lower.endsWith(".mkv") ||
            lower.endsWith(".webm")
}
