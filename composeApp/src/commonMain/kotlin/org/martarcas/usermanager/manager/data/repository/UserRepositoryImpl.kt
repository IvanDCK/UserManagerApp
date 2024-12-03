package org.martarcas.usermanager.manager.data.repository

import org.koin.core.annotation.Single
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.manager.data.dto.UserPublicDto
import org.martarcas.usermanager.manager.data.remote.network.UserApi
import org.martarcas.usermanager.manager.domain.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
): UserRepository {
    override suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote> {
        return userApi.getAllUsers()
    }

}