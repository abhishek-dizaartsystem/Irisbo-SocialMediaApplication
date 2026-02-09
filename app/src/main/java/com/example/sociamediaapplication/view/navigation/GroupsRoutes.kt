package com.example.sociamediaapplication.view.navigation

sealed class GroupsRoutes(val route: String) {
    object Groups: GroupsRoutes("groups")
    object Group: GroupsRoutes("group/{groupId}"){
        fun createRoute(groupId: String) = "group/$groupId"
    }
    object CreateGroup: GroupsRoutes("createGroup")
}