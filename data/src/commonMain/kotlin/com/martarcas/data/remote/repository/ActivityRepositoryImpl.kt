package com.martarcas.data.remote.repository

import com.alejandroarcas.core.requests.activity.CreateActivityLogRequest
import com.martarcas.data.mappers.toActivityLog
import com.martarcas.data.remote.network.UserApi
import com.martarcas.domain.model.activity.ActivityLog
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.model.response.map
import com.martarcas.domain.repository.ActivityRepository
import org.koin.core.annotation.Single

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