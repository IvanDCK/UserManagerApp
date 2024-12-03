package org.martarcas.usermanager.manager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.data.dto.requests.DeleteUserRequest
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun invoke(deleteUserRequest: DeleteUserRequest) = userRepository.deleteUser(deleteUserRequest)
}