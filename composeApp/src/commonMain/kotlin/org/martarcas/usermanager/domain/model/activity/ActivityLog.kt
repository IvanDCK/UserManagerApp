package org.martarcas.usermanager.domain.model.activity

data class ActivityLog(
    val userName: String,
    val activityType: String,
    val activityRole: String,
    val targetUserName: String,
    val actionTimestamp: String
)
