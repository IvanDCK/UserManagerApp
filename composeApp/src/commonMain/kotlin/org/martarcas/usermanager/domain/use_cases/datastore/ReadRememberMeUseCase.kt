package org.martarcas.usermanager.domain.use_cases.datastore

import kotlinx.coroutines.flow.Flow
import org.martarcas.usermanager.domain.model.repository.DataStoreRepository

class ReadRememberMeUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readRememberMe()
    }
}