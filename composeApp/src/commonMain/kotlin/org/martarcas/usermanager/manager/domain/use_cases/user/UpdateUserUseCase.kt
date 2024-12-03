package org.martarcas.usermanager.manager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class UpdateUserUseCase(
    private val userRepository: UserRepository
)  {
}