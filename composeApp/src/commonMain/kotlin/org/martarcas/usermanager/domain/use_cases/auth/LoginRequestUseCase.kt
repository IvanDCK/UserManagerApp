package org.martarcas.usermanager.domain.use_cases.auth
import org.koin.core.annotation.Single
import org.martarcas.usermanager.domain.mappers.toLoginUserRequest
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.repository.UserRepository

@Single
class LoginRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(user: User): Result<User, DataError.Remote> {
        return userRepository.login(user.toLoginUserRequest())
    }
}