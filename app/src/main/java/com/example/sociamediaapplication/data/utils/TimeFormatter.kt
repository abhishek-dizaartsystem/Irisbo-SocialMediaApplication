package com.example.sociamediaapplication.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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