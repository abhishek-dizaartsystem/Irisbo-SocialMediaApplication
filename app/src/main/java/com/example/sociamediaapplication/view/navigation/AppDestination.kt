package com.example.sociamediaapplication.view.navigation

sealed class AppDestination {
    data class Video( // testing for video
        val videoId: Int
    ) : AppDestination()

    data class Profile( // testing for profile
        val userId: Int
    ) : AppDestination()

    data class Post( //testing for post
        val postId: Int
    ) : AppDestination()

    data class Reel( //testing for reel
        val reelId: Int
    ) : AppDestination()

    data class Event( //testing for event
        val eventId: Int
    ) : AppDestination()
}