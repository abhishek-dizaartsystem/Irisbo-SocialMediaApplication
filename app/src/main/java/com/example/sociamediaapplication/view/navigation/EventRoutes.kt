package com.example.sociamediaapplication.view.navigation

sealed class EventRoutes(val route: String) {
    object Events: EventRoutes("events")
    object Event: EventRoutes("event/{eventId}/{isCreator}"){
        fun createRoute(eventId: Int, isCreator: Boolean) = "event/$eventId/$isCreator"
    }
    object CreateEvent: EventRoutes("createEvent/{eventId}/{isCreator}"){
        fun createRoute(eventId: Int, isCreator: Boolean) = "createEvent/$eventId/$isCreator"
    }
}