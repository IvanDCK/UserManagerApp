package org.martarcas.usermanager.manager.data.repository

import org.koin.core.annotation.Single
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.manager.data.dto.UserDto
import org.martarcas.usermanager.manager.data.dto.UserPublicDto
import org.martarcas.usermanager.manager.data.dto.requests.DeleteUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.GetUserByIdRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateRoleRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.CreateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.LoginUserRequest
import org.martarcas.usermanager.manager.data.remote.network.UserApi
import org.martarcas.usermanager.manager.domain.UserRepository

@Single
class UserRepositoryImpl(
    private val userApi: UserApi
): UserRepository {
    override suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote> {
        return userApi.getAllUsers()
    }

    override suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublicDto, DataError.Remote> {
        return userApi.getUserById(getUserByIdRequest)
    }

    override suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<UserDto, DataError.Remote> {
        return userApi.getLoggedUser(getUserByIdRequest)
    }

    override suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.registerUser(createUserRequest)
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.updateUser(updateUserRequest)
    }

    override suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.deleteUser(deleteUserRequest)
    }

    override suspend fun login(loginRequest: LoginUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.login(loginRequest)
    }

    override suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Boolean, DataError.Remote> {
       return userApi.changeRole(updateRoleRequest)
    }

}