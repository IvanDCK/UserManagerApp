package com.martarcas.domain.use_cases.auth
import com.alejandroarcas.core.requests.auth.CreateUserRequest
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class SignUpRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(user: CreateUserRequest): Result<Unit, DataError.Remote> {
        return userRepository.registerUser(user)
    }
}