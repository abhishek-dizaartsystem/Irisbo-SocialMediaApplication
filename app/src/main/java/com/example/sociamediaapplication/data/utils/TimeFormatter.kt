package com.example.sociamediaapplication.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.max

fun formatPostTime(isoDate: String): String {
    return try {

        val parser = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        parser.timeZone = TimeZone.getTimeZone("UTC")

        val postDate = parser.parse(isoDate) ?: return ""

        val now = Date()
        val diff = now.time - postDate.time

        val minutes = diff / (60 * 1000)
        val hours = diff / (60 * 60 * 1000)
        val days = diff / (24 * 60 * 60 * 1000)

        when {
            minutes < 1 -> "just now"

            minutes < 60 ->
                "${minutes}m"

            hours < 24 ->
                "${hours}h"

            days < 30 ->
                "${days}d"

            days < 365 ->
                "${days / 30}mo"

            else ->
                "${days / 365}y"
        }

    } catch (e: Exception) {
        ""
    }
}