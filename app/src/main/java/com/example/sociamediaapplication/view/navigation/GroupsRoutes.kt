package com.example.sociamediaapplication.view.navigation

sealed class GroupsRoutes(val route: String) {
    object Groups: GroupsRoutes("groups")
    object Group: GroupsRoutes("group/{groupId}/{isCreator}"){
        fun createRoute(groupId: Int, isCreator: Boolean) = "group/$groupId/$isCreator"
    }
    object CreateGroup: GroupsRoutes("createGroup")

    object CreateGroupPost: GroupsRoutes("createGroupPost/{groupId}") {
        fun createRoute(groupId: Int) = "createGroupPost/$groupId"
    }

    object EditGroup: GroupsRoutes("editGroup/{groupId}"){
        fun createRoute(groupId: Int) = "editGroup/$groupId"
    }
}