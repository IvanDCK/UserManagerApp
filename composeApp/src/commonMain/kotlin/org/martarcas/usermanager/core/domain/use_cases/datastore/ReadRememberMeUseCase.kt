package org.martarcas.usermanager.core.domain.use_cases.datastore

import kotlinx.coroutines.flow.Flow
import org.martarcas.usermanager.core.domain.DataStoreRepository


class ReadRememberMeUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readRememberMe()
    }
}