package org.martarcas.usermanager.core.domain.use_cases.datastore

import kotlinx.coroutines.flow.Flow
import org.martarcas.usermanager.core.domain.DataStoreRepository
import org.martarcas.usermanager.manager.domain.model.user.User


class ReadUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<User> {
        return dataStoreRepository.readUserData()
    }
}