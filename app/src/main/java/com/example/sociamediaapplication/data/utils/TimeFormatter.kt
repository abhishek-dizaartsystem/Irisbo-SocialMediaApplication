package com.example.sociamediaapplication.data.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun formatToPostTime(dateTime: String): String {

    return try {

        val inputFormat = java.text.SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            java.util.Locale.getDefault()
        )

        val uploadedDate = inputFormat.parse(dateTime)
            ?: return dateTime

        val currentTime = System.currentTimeMillis()

        val diffMillis = currentTime - uploadedDate.time

        val seconds = diffMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        when {

            years > 0 -> {
                if (years == 1L) "1 year ago"
                else "$years years ago"
            }

            months > 0 -> {
                if (months == 1L) "1 month ago"
                else "$months months ago"
            }

            weeks > 0 -> {
                if (weeks == 1L) "1 week ago"
                else "$weeks weeks ago"
            }

            days > 0 -> {
                if (days == 1L) "1 day ago"
                else "$days days ago"
            }

            hours > 0 -> {
                if (hours == 1L) "1 hour ago"
                else "$hours hours ago"
            }

            minutes > 0 -> {
                if (minutes == 1L) "1 minute ago"
                else "$minutes minutes ago"
            }

            else -> {
                "Just now"
            }
        }

    } catch (e: Exception) {
        dateTime
    }
}
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

fun formatToDate(isoDate: String): String {
    return try{
        val date = isoDate.split("T")[0]
        val year = date.split("-")[0]
        val month = date.split("-")[1]
        val day = date.split("-")[2]

        val monthName = when(month.toInt()){
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }
        "$day $monthName $year"
    } catch (e: Exception){
        isoDate
    }
}

fun formatToTime(isoDate: String): String{
    return try{
        val time = isoDate.split("T")[1]
        val hour = time.split(":")[0]
        val minute = time.split(":")[1]

        if(hour.toInt() >= 12){
            "${hour.toInt() - 12}:${minute} PM"
        } else{
            "${hour.toInt()}:${minute} AM"
        }
    } catch(e: Exception){
        isoDate
    }
}

fun formatToTime2(date: String): String {

    return try {

        val time = date.split(" ")[1]

        val parts = time.split(":")

        val hour24 = parts[0].toInt()
        val minute = parts[1]

        val amPm = if (hour24 >= 12) "PM" else "AM"

        val hour12 = when {
            hour24 == 0 -> 12
            hour24 > 12 -> hour24 - 12
            else -> hour24
        }

        "$hour12:$minute $amPm"

    } catch (e: Exception) {

        Log.e("FormatTime2_DEBUG", e.message.toString())
        date
    }
}

fun formatToDate2(dateTime: String): String {

    return try {

        val inputFormat = java.text.SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            java.util.Locale.getDefault()
        )

        val outputFormat = java.text.SimpleDateFormat(
            "dd MMM yyyy",
            java.util.Locale.getDefault()
        )

        val parsedDate = inputFormat.parse(dateTime) ?: return dateTime

        val calendar = java.util.Calendar.getInstance()
        val today = java.util.Calendar.getInstance()
        val yesterday = java.util.Calendar.getInstance()

        yesterday.add(java.util.Calendar.DATE, -1)

        calendar.time = parsedDate

        when {

            // ✅ TODAY
            calendar.get(java.util.Calendar.YEAR) == today.get(java.util.Calendar.YEAR) &&
                    calendar.get(java.util.Calendar.DAY_OF_YEAR) == today.get(java.util.Calendar.DAY_OF_YEAR) -> {
                "Today"
            }

            // ✅ YESTERDAY
            calendar.get(java.util.Calendar.YEAR) == yesterday.get(java.util.Calendar.YEAR) &&
                    calendar.get(java.util.Calendar.DAY_OF_YEAR) == yesterday.get(java.util.Calendar.DAY_OF_YEAR) -> {
                "Yesterday"
            }

            // ✅ NORMAL DATE
            else -> {
                outputFormat.format(parsedDate)
            }
        }

    } catch (e: Exception) {
        dateTime
    }
}

fun convertToBackendFormat(date: String, time: String): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("dd-MM-yyyy HH:mm", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())

        val dateObj = inputFormat.parse("$date $time")
        outputFormat.format(dateObj!!)
    } catch (e: Exception) {
        ""
    }
}

fun convertToDuration(seconds: Int): String {

    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return if (hours > 0) {
        String.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            remainingSeconds
        )
    } else {
        String.format(
            "%02d:%02d",
            minutes,
            remainingSeconds
        )
    }
}