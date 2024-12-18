package com.martarcas.domain.use_cases.user

import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single


@Single
open class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.getAllUsers()
}