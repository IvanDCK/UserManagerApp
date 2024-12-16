package org.martarcas.usermanager.domain.use_cases.activity

import org.koin.core.annotation.Single
import org.martarcas.usermanager.domain.repository.ActivityRepository

@Single
class GetAllActivityLogsUseCase(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke() = activityRepository.getAllActivityLogs()
}