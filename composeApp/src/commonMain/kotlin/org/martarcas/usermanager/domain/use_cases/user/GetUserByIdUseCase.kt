package org.martarcas.usermanager.domain.use_cases.user

import org.koin.core.annotation.Single
import org.martarcas.usermanager.data.remote.requests.GetUserByIdRequest
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.repository.UserRepository

@Single
class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int): Result<User, DataError.Remote> {
        return userRepository.getLoggedUser(GetUserByIdRequest(userId))
    }
}