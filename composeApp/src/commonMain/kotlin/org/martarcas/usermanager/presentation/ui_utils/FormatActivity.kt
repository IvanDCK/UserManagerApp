package org.martarcas.usermanager.presentation.ui_utils

import kotlinx.datetime.Clock

fun formatActivityType(activityType: String): String {
    return when (activityType) {
        "ChangeRole" -> "Role updated"
        "DeleteUser" -> "User deleted"
        "CreatedUser" -> "User created"
        else -> "Info updated"
    }
}

fun formatTimestampDifference(timestampInSeconds: Long): String {
    val now = Clock.System.now().toEpochMilliseconds()
    val durationInSeconds = (now - timestampInSeconds) / 1000

    return when {
        durationInSeconds < 60 -> "A few seconds ago"
        durationInSeconds < 3600 -> pluralize(durationInSeconds / 60, "minute")
        durationInSeconds < 86400 -> pluralize(durationInSeconds / 3600, "hour")
        durationInSeconds < 2592000 -> pluralize(durationInSeconds / 86400, "day")
        durationInSeconds < 31536000 -> pluralize(durationInSeconds / 2592000, "month")
        else -> pluralize(durationInSeconds / 31536000, "year")
    }
}

private fun pluralize(value: Long, unit: String): String {
    return if (value == 1L) "1 $unit ago" else "$value ${unit}s ago"
}