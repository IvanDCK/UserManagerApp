package com.martarcas.domain.use_cases.auth
import com.alejandroarcas.core.requests.auth.LoginUserRequest
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.model.user.User
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single


@Single
class LoginRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(user: LoginUserRequest): Result<User, DataError.Remote> {
        return userRepository.login(user)
    }
}