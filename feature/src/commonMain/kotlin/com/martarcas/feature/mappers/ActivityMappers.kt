package com.martarcas.feature.mappers

import com.martarcas.domain.model.activity.ActivityLog
import com.martarcas.feature.activity.model.ActivityUiModel


fun ActivityLog.toActivityUiModel(): ActivityUiModel {
    return ActivityUiModel(
        userName = userName,
        activityType = activityType,
        activityRole = activityRole,
        targetUserName = targetUserName,
        actionTimestamp = actionTimestamp
    )
}