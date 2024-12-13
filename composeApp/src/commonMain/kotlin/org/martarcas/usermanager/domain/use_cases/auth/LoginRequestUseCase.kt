package org.martarcas.usermanager.domain.use_cases.auth
import org.koin.core.annotation.Single
import org.martarcas.usermanager.data.remote.requests.auth.LoginUserRequest
import org.martarcas.usermanager.domain.model.repository.UserRepository
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result
import org.martarcas.usermanager.domain.model.user.User

@Single
class LoginRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(loginUserRequest: LoginUserRequest): Result<User, DataError.Remote> {
        return userRepository.login(loginUserRequest)
    }
}