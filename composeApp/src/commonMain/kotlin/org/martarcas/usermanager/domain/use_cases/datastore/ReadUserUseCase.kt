package org.martarcas.usermanager.domain.use_cases.datastore

import kotlinx.coroutines.flow.Flow
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.repository.DataStoreRepository

class ReadUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<User> {
        return dataStoreRepository.readUserData()
    }
}