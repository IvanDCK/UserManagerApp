package org.martarcas.usermanager.presentation.mappers

import org.martarcas.usermanager.domain.model.activity.ActivityLog
import org.martarcas.usermanager.presentation.activity.model.ActivityUiModel


fun ActivityLog.toActivityUiModel(): ActivityUiModel {
    return ActivityUiModel(
        userName = userName,
        activityType = activityType,
        activityRole = activityRole,
        targetUserName = targetUserName,
        actionTimestamp = actionTimestamp
    )
}