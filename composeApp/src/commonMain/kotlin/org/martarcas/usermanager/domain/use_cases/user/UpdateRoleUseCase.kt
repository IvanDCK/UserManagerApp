package org.martarcas.usermanager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.data.remote.requests.UpdateRoleRequest
import org.martarcas.usermanager.domain.model.repository.UserRepository

@Single
class UpdateRoleUseCase(
    private val userRepository: UserRepository
)  {
    suspend fun invoke(updateRoleRequest: UpdateRoleRequest) = userRepository.changeRole(updateRoleRequest)
}