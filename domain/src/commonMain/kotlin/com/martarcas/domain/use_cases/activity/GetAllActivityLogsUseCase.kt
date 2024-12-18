package com.martarcas.domain.use_cases.activity

import com.martarcas.domain.repository.ActivityRepository
import org.koin.core.annotation.Single

@Single
class GetAllActivityLogsUseCase(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke() = activityRepository.getAllActivityLogs()
}