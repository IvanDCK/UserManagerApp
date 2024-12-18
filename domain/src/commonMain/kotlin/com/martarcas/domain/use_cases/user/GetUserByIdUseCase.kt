package com.martarcas.domain.use_cases.user

import com.alejandroarcas.core.requests.GetUserByIdRequest
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.model.user.User
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single


@Single
class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int): Result<User, DataError.Remote> {
        return userRepository.getLoggedUser(GetUserByIdRequest(userId))
    }
}