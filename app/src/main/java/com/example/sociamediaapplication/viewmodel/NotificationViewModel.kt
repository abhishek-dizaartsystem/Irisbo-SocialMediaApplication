package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository
): ViewModel() {

    fun saveFcmToken(
        deviceId: String,
        fcmToken: String
    ) {

        viewModelScope.launch {

            try {

                repository.saveFcmToken(
                    deviceId,
                    fcmToken
                )

                Log.d(
                    "FCM_DEBUG",
                    "FCM token saved"
                )

            } catch (e: Exception) {

                Log.e(
                    "FCM_DEBUG",
                    e.message.toString()
                )
            }
        }
    }

    fun removeFcmToken(
        deviceId: String
    ) {

        viewModelScope.launch {

            try {

                repository.removeFcmToken(
                    deviceId
                )

            } catch (e: Exception) {

                Log.e(
                    "FCM_DEBUG",
                    e.message.toString()
                )
            }
        }
    }
}