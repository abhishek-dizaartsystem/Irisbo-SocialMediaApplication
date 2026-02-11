package com.example.sociamediaapplication.view.navigation

sealed class SettingsRoutes(val route: String) {
    object EditProfile: SettingsRoutes("editProfile")
    object Security: SettingsRoutes("security")
    object Privacy: SettingsRoutes("privacy")
    object Notification: SettingsRoutes("notification")
    object Appearance: SettingsRoutes("appearance")
    object Language: SettingsRoutes("language")
    object HelpCenter: SettingsRoutes("helpCenter")
}