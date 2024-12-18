package com.martarcas.domain.use_cases.datastore

import com.martarcas.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadRememberMeUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readRememberMe()
    }
}