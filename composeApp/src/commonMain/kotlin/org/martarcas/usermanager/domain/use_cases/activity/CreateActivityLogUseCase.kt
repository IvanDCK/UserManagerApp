package org.martarcas.usermanager.domain.use_cases.activity

import org.koin.core.annotation.Single
import org.martarcas.usermanager.domain.mappers.toCreateActivityRequest
import org.martarcas.usermanager.domain.model.activity.ActivityLog
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result
import org.martarcas.usermanager.domain.repository.ActivityRepository

@Single
class CreateActivityLogUseCase(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(activityLog: ActivityLog): Result<Unit, DataError.Remote> {
        return activityRepository.createActivityLog(activityLog.toCreateActivityRequest())
    }
}