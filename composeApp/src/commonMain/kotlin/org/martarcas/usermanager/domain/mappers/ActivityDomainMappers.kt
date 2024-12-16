package org.martarcas.usermanager.domain.mappers

import org.martarcas.usermanager.data.remote.requests.activity.CreateActivityLogRequest
import org.martarcas.usermanager.domain.model.activity.ActivityLog

fun ActivityLog.toCreateActivityRequest(): CreateActivityLogRequest {
    return CreateActivityLogRequest(
        userName = userName,
        activityType = activityType,
        activityRole = activityRole,
        targetUserName = targetUserName
    )
}

/*
fun ActivityLog.toActivityUiModel(): ActivityUiModel {
    return ActivityUiModel(
        userId = userId,
        activityType = activityType,
        activityRole = activityRole,
        targetUserId = targetUserId,
        actionTimestamp = actionTimestamp
    )
}*/
