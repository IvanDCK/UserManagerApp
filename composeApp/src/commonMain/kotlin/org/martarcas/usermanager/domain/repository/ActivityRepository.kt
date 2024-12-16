package org.martarcas.usermanager.domain.repository

import org.martarcas.usermanager.data.remote.requests.activity.CreateActivityLogRequest
import org.martarcas.usermanager.domain.model.activity.ActivityLog
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result

interface ActivityRepository {
    suspend fun createActivityLog(createActivityLogRequest: CreateActivityLogRequest): Result<Unit, DataError.Remote>
    suspend fun getAllActivityLogs(): Result<List<ActivityLog>, DataError.Remote>
}