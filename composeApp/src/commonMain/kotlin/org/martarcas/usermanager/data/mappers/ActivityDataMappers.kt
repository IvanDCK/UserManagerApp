package org.martarcas.usermanager.data.mappers

import org.martarcas.usermanager.data.remote.dto.ActivityLogDto
import org.martarcas.usermanager.domain.model.activity.ActivityLog

fun ActivityLogDto.toActivityLog(): ActivityLog {
    return ActivityLog(
        userName = userName,
        activityType = activityType,
        activityRole = activityRole,
        targetUserName = targetUserName,
        actionTimestamp = actionTimestamp
    )
}