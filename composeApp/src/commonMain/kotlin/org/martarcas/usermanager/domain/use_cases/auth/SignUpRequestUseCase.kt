package org.martarcas.usermanager.domain.use_cases.auth
import org.koin.core.annotation.Single
import org.martarcas.usermanager.data.remote.requests.auth.CreateUserRequest
import org.martarcas.usermanager.domain.model.repository.UserRepository
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result

@Single
class SignUpRequestUseCase(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(createRequest: CreateUserRequest): Result<Unit, DataError.Remote> {
        return userRepository.registerUser(createRequest)
    }
}