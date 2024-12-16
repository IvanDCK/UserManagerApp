package org.martarcas.usermanager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.domain.repository.UserRepository


@Single
open class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.getAllUsers()
}