package org.martarcas.usermanager.data.remote.repository

import org.koin.core.annotation.Single
import org.martarcas.usermanager.data.mappers.toActivityLog
import org.martarcas.usermanager.data.remote.network.UserApi
import org.martarcas.usermanager.data.remote.requests.activity.CreateActivityLogRequest
import org.martarcas.usermanager.domain.model.activity.ActivityLog
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result
import org.martarcas.usermanager.domain.model.response.map
import org.martarcas.usermanager.domain.repository.ActivityRepository

@Single
class ActivityRepositoryImpl(
    private val userApi: UserApi
): ActivityRepository {
    override suspend fun createActivityLog(createActivityLogRequest: CreateActivityLogRequest): Result<Unit, DataError.Remote> {
        return userApi.createActivityLog(createActivityLogRequest)
    }

    override suspend fun getAllActivityLogs(): Result<List<ActivityLog>, DataError.Remote> {
        return userApi.getAllActivityLogs().map { activityLogDto ->
            activityLogDto.map {
                it.toActivityLog()
            }
        }
    }
}