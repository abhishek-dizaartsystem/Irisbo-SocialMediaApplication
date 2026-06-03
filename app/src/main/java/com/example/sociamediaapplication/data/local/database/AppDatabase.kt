package com.example.sociamediaapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sociamediaapplication.data.local.database.DownloadedVideoDao
import com.example.sociamediaapplication.data.local.database.DownloadedVideoEntity

@Database(
    entities = [DownloadedVideoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun downloadedVideoDao(): DownloadedVideoDao
}