package com.martarcas.data.mappers

import com.martarcas.data.remote.dto.ActivityLogDto
import com.martarcas.domain.model.activity.ActivityLog

fun ActivityLogDto.toActivityLog(): ActivityLog {
    return ActivityLog(
        userName = userName,
        activityType = activityType,
        activityRole = activityRole,
        targetUserName = targetUserName,
        actionTimestamp = actionTimestamp
    )
}