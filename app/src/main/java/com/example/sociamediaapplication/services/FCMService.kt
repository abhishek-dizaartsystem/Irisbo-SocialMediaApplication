package com.example.sociamediaapplication.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sociamediaapplication.MainActivity
import com.example.sociamediaapplication.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // 🔥 optional:
        // resend updated token to backend
    }

//    override fun onMessageReceived(
//        message: RemoteMessage
//    ) {
//
//        super.onMessageReceived(message)
//
//        val title =
//            message.notification?.title
//                ?: "Notification"
//
//        val body =
//            message.notification?.body
//                ?: ""
//
//        val conversationId =
//            message.data["conversationId"]
//
//        showNotification(
//            title,
//            body,
//            conversationId
//        )
//    }

    override fun onMessageReceived(
        message: RemoteMessage
    ) {

        super.onMessageReceived(message)

        val title =
            message.notification?.title
                ?: "Notification"

        val body =
            message.notification?.body
                ?: ""

        val entityType =
            message.data["entity_type"]

        val entityId =
            message.data["entity_id"]

        showNotification(
            title,
            body,
            entityType,
            entityId
        )
    }

    private fun showNotification(
        title: String,
        body: String,
        entityType: String?,
        entityId: String?
    ) {

        val channelId =
            "social_notifications"

        val manager =
            getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "Social Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            manager.createNotificationChannel(channel)
        }

        val intent =
            Intent(
                this,
                MainActivity::class.java
            )

        intent.putExtra(
            "entity_type",
            entityType
        )

        intent.putExtra(
            "entity_id",
            entityId
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or
                        PendingIntent.FLAG_UPDATE_CURRENT
            )

        val notification =
            NotificationCompat.Builder(
                this,
                channelId
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        manager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )

        Log.d(
            "FCM_DEBUG",
            "type=$entityType id=$entityId"
        )
    }
}