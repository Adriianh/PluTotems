package com.github.adriianh.common.util

import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object TimeUtil {
    private val timePattern = Pattern.compile("(?:(\\d+)y)?(?:(\\d+)M)?(?:(\\d+)d)?(?:(\\d+)h)?(?:(\\d+)m)?(?:(\\d+)s)?")

    fun parseTimeToMillis(timeString: String): Long {
        val matcher = timePattern.matcher(timeString)
        if (!matcher.matches()) {
            throw IllegalArgumentException("Invalid time format: $timeString")
        }

        val years = matcher.group(1)?.toLongOrNull() ?: 0L
        val months = matcher.group(2)?.toLongOrNull() ?: 0L
        val days = matcher.group(3)?.toLongOrNull() ?: 0L
        val hours = matcher.group(4)?.toLongOrNull() ?: 0L
        val minutes = matcher.group(5)?.toLongOrNull() ?: 0L
        val seconds = matcher.group(6)?.toLongOrNull() ?: 0L

        val millisInYear = 31536000000L
        val millisInMonth = 2592000000L

        return (years * millisInYear) + (months * millisInMonth) + (days * 86400000) + (hours * 3600000) + (minutes * 60000) + (seconds * 1000)
    }

    fun formatTime(milliseconds: Long): String {
        val timeComponents = calculateTimeComponents(milliseconds)
        return formatTimeComponents(*timeComponents.toLongArray())
    }

    private fun calculateTimeComponents(milliseconds: Long): List<Long> {
        val daysInYear = 365
        val daysInMonth = 30

        val totalDays = TimeUnit.MILLISECONDS.toDays(milliseconds)
        val years = totalDays / daysInYear
        val months = (totalDays % daysInYear) / daysInMonth
        val days = totalDays % daysInMonth
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60

        return listOf(years, months, days, hours, minutes, seconds)
    }

    private fun formatTimeComponents(vararg components: Long): String {
        val labels = arrayOf("y", "M", "d", "h", "m", "s")
        val timeComponents = StringBuilder()

        for (i in components.indices) {
            if (components[i] > 0) {
                if (timeComponents.isNotEmpty()) timeComponents.append(" ")
                timeComponents.append("${components[i]}${labels[i]}")
            }
        }

        return timeComponents.toString()
    }
}