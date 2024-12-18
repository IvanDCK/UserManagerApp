package com.alejandroarcas.core.requests.activity

import kotlinx.serialization.Serializable

@Serializable
data class CreateActivityLogRequest(
    val userName: String,
    val activityType: String,
    val activityRole: String,
    val targetUserName: String,
)
