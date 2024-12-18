package com.martarcas.domain.use_cases.auth
import com.martarcas.domain.mappers.toCreateUserRequest
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.model.user.User
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class SignUpRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(user: User): Result<Unit, DataError.Remote> {
        return userRepository.registerUser(user.toCreateUserRequest())
    }
}