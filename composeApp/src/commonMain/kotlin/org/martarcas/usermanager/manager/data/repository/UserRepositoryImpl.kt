package org.martarcas.usermanager.manager.data.repository

import org.koin.core.annotation.Single
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.core.domain.map
import org.martarcas.usermanager.manager.data.dto.requests.DeleteUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.GetUserByIdRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateRoleRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.CreateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.LoginUserRequest
import org.martarcas.usermanager.manager.data.mappers.toUser
import org.martarcas.usermanager.manager.data.mappers.toUserPublic
import org.martarcas.usermanager.manager.data.remote.network.UserApi
import org.martarcas.usermanager.manager.domain.UserRepository
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.model.user.UserPublic

@Single
class UserRepositoryImpl(
    private val userApi: UserApi
): UserRepository {
    override suspend fun getAllUsers(): Result<List<UserPublic>, DataError.Remote> {
        return userApi.getAllUsers().map { usersPublicDto ->
            usersPublicDto.map { userPublicDto ->
                userPublicDto.toUserPublic()
            }
        }
    }

    override suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublic, DataError.Remote> {
        return userApi.getUserById(getUserByIdRequest).map { userPublicDto ->
            userPublicDto.toUserPublic()
        }
    }

    override suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<User, DataError.Remote> {
        return userApi.getLoggedUser(getUserByIdRequest).map { userDto ->
            userDto.toUser()
        }
    }

    override suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.registerUser(createUserRequest)
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.updateUser(updateUserRequest)
    }

    override suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.deleteUser(deleteUserRequest).map { true }
    }

    override suspend fun login(loginRequest: LoginUserRequest): Result<Boolean, DataError.Remote> {
        return userApi.login(loginRequest)
    }

    override suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Boolean, DataError.Remote> {
       return userApi.changeRole(updateRoleRequest)
    }

}