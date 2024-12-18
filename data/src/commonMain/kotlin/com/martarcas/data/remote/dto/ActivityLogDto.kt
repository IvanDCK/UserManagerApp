package com.martarcas.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActivityLogDto(
    val userName: String,
    val activityType: String,
    val activityRole: String,
    val targetUserName: String,
    val actionTimestamp: String
)