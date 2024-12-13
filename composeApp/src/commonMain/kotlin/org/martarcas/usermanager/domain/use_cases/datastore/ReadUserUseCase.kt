package org.martarcas.usermanager.domain.use_cases.datastore

import kotlinx.coroutines.flow.Flow
import org.martarcas.usermanager.domain.model.repository.DataStoreRepository
import org.martarcas.usermanager.domain.model.user.User

class ReadUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<User> {
        return dataStoreRepository.readUserData()
    }
}