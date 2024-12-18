package com.martarcas.feature.presentation.activity.model

data class ActivityUiModel(
    val userName: String,
    val activityType: String,
    val activityRole: String,
    val targetUserName: String,
    val actionTimestamp: String
)
