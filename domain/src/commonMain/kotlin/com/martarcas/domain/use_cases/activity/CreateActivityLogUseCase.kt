package com.martarcas.domain.use_cases.activity

import com.alejandroarcas.core.requests.activity.CreateActivityLogRequest
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.repository.ActivityRepository
import org.koin.core.annotation.Single


@Single
class CreateActivityLogUseCase(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(activityLog: CreateActivityLogRequest): Result<Unit, DataError.Remote> {
        return activityRepository.createActivityLog(activityLog)
    }
}