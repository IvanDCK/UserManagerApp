package org.martarcas.usermanager.manager.domain.use_cases.auth
import org.koin.core.annotation.Single
import org.martarcas.usermanager.core.domain.model.DataError
import org.martarcas.usermanager.core.domain.model.Result
import org.martarcas.usermanager.manager.data.remote.requests.auth.LoginUserRequest
import org.martarcas.usermanager.manager.domain.UserRepository
import org.martarcas.usermanager.manager.domain.model.user.User

@Single
class LoginRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(loginUserRequest: LoginUserRequest): Result<User, DataError.Remote> {
        return userRepository.login(loginUserRequest)
    }
}