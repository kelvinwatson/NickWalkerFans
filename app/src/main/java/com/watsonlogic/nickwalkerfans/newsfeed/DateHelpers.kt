package com.watsonlogic.nickwalkerfans.newsfeed

import com.watsonlogic.nickwalkerfans.newsfeed.DateConstants.ISO_8601_STRING_PATTERN
import com.watsonlogic.nickwalkerfans.newsfeed.DateConstants.READABLE_PATTERN
import com.watsonlogic.nickwalkerfans.util.CharConstants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String?.iso8601ToFormattedDate(): String {
    val locale = Locale.getDefault()
    val desiredFormat = SimpleDateFormat(READABLE_PATTERN, locale)
    val isoFormat = SimpleDateFormat(ISO_8601_STRING_PATTERN, locale)
    val isoString = this ?: isoFormat.format(Date())
    val date = isoFormat.parse(isoString)
    return date?.let { desiredFormat.format(it) } ?: CharConstants.EMPTY_STRING
}

object DateConstants {
    const val READABLE_PATTERN = "MMM. dd, yyyy HH:mm"
    const val ISO_8601_STRING_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
}