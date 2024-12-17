package org.martarcas.usermanager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.data.remote.requests.DeleteUserRequest
import org.martarcas.usermanager.domain.repository.UserRepository

@Single
class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(deleteUserRequest: DeleteUserRequest) = userRepository.deleteUser(deleteUserRequest)
}