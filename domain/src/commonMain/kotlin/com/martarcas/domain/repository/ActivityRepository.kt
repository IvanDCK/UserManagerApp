package com.martarcas.domain.repository

import com.alejandroarcas.core.requests.activity.CreateActivityLogRequest
import com.martarcas.domain.model.activity.ActivityLog
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result

interface ActivityRepository {
    suspend fun createActivityLog(createActivityLogRequest: CreateActivityLogRequest): Result<Unit, DataError.Remote>
    suspend fun getAllActivityLogs(): Result<List<ActivityLog>, DataError.Remote>
}