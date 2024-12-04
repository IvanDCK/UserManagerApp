package org.martarcas.usermanager.manager.domain.use_cases.auth
import org.koin.core.annotation.Single
import org.martarcas.usermanager.core.domain.model.DataError
import org.martarcas.usermanager.core.domain.model.Result
import org.martarcas.usermanager.manager.data.remote.requests.auth.CreateUserRequest
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class SignUpRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(createRequest: CreateUserRequest): Result<Unit, DataError.Remote> {
        return userRepository.registerUser(createRequest)
    }
}