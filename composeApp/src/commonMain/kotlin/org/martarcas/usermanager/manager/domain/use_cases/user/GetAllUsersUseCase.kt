package org.martarcas.usermanager.manager.domain.use_cases.user

import org.martarcas.usermanager.manager.domain.UserRepository

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.getAllUsers()
}