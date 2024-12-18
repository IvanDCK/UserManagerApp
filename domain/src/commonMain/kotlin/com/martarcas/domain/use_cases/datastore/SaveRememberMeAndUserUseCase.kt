package com.martarcas.domain.use_cases.datastore

import com.martarcas.domain.model.user.User
import com.martarcas.domain.repository.DataStoreRepository

class SaveRememberMeAndUserUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(rememberMe: Boolean, user: User) {
        dataStoreRepository.saveRememberMeAndUserData(rememberMe, user)
    }
}