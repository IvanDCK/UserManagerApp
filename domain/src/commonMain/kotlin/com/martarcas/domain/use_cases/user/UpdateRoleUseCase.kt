package com.martarcas.domain.use_cases.user

import com.alejandroarcas.core.requests.UpdateRoleRequest
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class UpdateRoleUseCase(
    private val userRepository: UserRepository
)  {
    suspend fun invoke(updateRoleRequest: UpdateRoleRequest) = userRepository.changeRole(updateRoleRequest)
}