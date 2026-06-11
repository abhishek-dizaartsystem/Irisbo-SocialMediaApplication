package com.example.sociamediaapplication.data.preferences

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.sociamediaapplication.view.navigation.AppDestination




object NavigationManager {
    var pendingDestination by mutableStateOf<AppDestination?>(null)
}