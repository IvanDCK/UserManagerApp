package com.martarcas.domain.mappers

import com.alejandroarcas.core.requests.activity.CreateActivityLogRequest
import com.martarcas.domain.model.activity.ActivityLog

fun ActivityLog.toCreateActivityRequest(): CreateActivityLogRequest {
    return CreateActivityLogRequest(
        userName = userName,
        activityType = activityType,
        activityRole = activityRole,
        targetUserName = targetUserName
    )
}