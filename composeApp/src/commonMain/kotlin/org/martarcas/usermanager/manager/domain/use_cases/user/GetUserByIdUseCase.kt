package org.martarcas.usermanager.manager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class GetUserByIdUseCase(
    private val userRepository: UserRepository
)  {
}