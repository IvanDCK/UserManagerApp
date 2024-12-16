package org.martarcas.usermanager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.data.remote.requests.UpdateUserRequest
import org.martarcas.usermanager.domain.repository.UserRepository

@Single
class UpdateUserUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(updateUserRequest: UpdateUserRequest) = userRepository.updateUser(updateUserRequest)

}