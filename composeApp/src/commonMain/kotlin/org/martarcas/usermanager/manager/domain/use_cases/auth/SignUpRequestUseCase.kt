package org.martarcas.usermanager.manager.domain.use_cases.auth
import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class SignUpRequestUseCase(
    private val userRepository: UserRepository
)  {
}