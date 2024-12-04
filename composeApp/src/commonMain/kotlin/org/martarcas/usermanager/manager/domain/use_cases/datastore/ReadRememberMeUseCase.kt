package org.martarcas.usermanager.manager.domain.use_cases.datastore

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.domain.DataStoreRepository

@Single
class ReadRememberMeUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readRememberMe()
    }
}