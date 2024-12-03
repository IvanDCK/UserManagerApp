package org.martarcas.usermanager.manager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.data.dto.requests.UpdateRoleRequest
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class UpdateRoleUseCase(
    private val userRepository: UserRepository
)  {
    suspend fun invoke(updateRoleRequest: UpdateRoleRequest) = userRepository.changeRole(updateRoleRequest)
}