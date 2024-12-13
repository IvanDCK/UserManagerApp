package org.martarcas.usermanager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.domain.model.repository.UserRepository

@Single
class GetUserByIdUseCase(
    private val userRepository: UserRepository
)  {
}