package com.example.sociamediaapplication.view.navigation

sealed class AppDestination {
    data class Video(
        val videoId: Int
    ) : AppDestination()

    data class Profile(
        val userId: Int
    ) : AppDestination()

    data class Post(
        val postId: Int
    ) : AppDestination()

    data class Reel(
        val reelId: Int
    ) : AppDestination()

    data class Event(
        val eventId: Int
    ) : AppDestination()
}