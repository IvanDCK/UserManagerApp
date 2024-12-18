package com.martarcas.domain.use_cases.user

import com.alejandroarcas.core.requests.UpdateUserRequest
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class UpdateUserUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(updateUserRequest: UpdateUserRequest) = userRepository.updateUser(updateUserRequest)

}