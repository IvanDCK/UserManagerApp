package org.martarcas.usermanager.manager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.domain.UserRepository


@Single
class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.getAllUsers()
}