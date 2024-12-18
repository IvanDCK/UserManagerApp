package com.martarcas.domain.use_cases.datastore

import com.martarcas.domain.model.user.User
import com.martarcas.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<User> {
        return dataStoreRepository.readUserData()
    }
}