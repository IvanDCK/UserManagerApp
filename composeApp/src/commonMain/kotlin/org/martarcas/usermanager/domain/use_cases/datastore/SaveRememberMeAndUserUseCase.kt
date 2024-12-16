package org.martarcas.usermanager.domain.use_cases.datastore

import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.repository.DataStoreRepository

class SaveRememberMeAndUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(rememberMe: Boolean, user: User) {
        dataStoreRepository.saveRememberMeAndUserData(rememberMe, user)
    }
}