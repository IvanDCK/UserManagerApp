package org.martarcas.usermanager.manager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.data.dto.requests.UpdateUserRequest
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class UpdateUserUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(updateUserRequest: UpdateUserRequest) = userRepository.updateUser(updateUserRequest)

}