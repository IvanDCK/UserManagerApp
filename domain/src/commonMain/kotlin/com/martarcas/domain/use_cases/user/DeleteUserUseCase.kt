package com.martarcas.domain.use_cases.user

import com.alejandroarcas.core.requests.DeleteUserRequest
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(deleteUserRequest: DeleteUserRequest) = userRepository.deleteUser(deleteUserRequest)
}