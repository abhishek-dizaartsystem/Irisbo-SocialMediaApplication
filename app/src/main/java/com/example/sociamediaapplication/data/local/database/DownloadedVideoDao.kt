package com.example.sociamediaapplication.data.local.database

import androidx.room.*

@Dao
interface DownloadedVideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(video: DownloadedVideoEntity)

    @Delete
    suspend fun delete(video: DownloadedVideoEntity)

    @Query("SELECT * FROM downloaded_videos ORDER BY downloadedAt DESC")
    suspend fun getAllVideos(): List<DownloadedVideoEntity>

    @Query("SELECT * FROM downloaded_videos WHERE videoId = :videoId LIMIT 1")
    suspend fun getVideo(videoId: Int): DownloadedVideoEntity?
}