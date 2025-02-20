package com.example.testwithpoetry.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat(MM_DD_YYYY_PATTERN, Locale.getDefault())
        return formatter.format(Date(millis))
    }

    private const val MM_DD_YYYY_PATTERN = "MM/dd/yyyy"
}
