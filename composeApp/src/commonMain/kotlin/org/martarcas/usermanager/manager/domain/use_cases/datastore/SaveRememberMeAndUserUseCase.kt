package org.martarcas.usermanager.manager.domain.use_cases.datastore

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.domain.DataStoreRepository
import org.martarcas.usermanager.manager.domain.model.user.User


class SaveRememberMeAndUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(rememberMe: Boolean, user: User) {
        dataStoreRepository.saveRememberMeAndUserData(rememberMe, user)
    }
}