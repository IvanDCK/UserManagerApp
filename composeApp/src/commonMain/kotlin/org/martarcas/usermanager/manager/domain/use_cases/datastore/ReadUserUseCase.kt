package org.martarcas.usermanager.manager.domain.use_cases.datastore

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.domain.DataStoreRepository
import org.martarcas.usermanager.manager.domain.model.user.User

@Single
class ReadUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<User> {
        return dataStoreRepository.readUserData()
    }
}