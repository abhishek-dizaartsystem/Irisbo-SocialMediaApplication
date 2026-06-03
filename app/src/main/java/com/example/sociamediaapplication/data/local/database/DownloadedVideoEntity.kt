package com.example.sociamediaapplication.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_videos")
data class DownloadedVideoEntity(

    @PrimaryKey
    val videoId: Int,

    val title: String,

    val thumbnailUrl: String,

    val videoUrl: String,

    val localPath: String,

    val downloadedAt: Long = System.currentTimeMillis()
)