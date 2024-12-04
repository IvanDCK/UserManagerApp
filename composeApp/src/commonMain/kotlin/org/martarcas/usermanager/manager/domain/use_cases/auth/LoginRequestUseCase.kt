package org.martarcas.usermanager.manager.domain.use_cases.auth
import org.koin.core.annotation.Single
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.manager.data.dto.UserDto
import org.martarcas.usermanager.manager.data.dto.requests.auth.LoginUserRequest
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class LoginRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(loginUserRequest: LoginUserRequest): Result<UserDto, DataError.Remote> {
        return userRepository.login(loginUserRequest)
    }
}