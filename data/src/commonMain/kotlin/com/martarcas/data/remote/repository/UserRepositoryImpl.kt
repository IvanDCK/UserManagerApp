package com.martarcas.data.remote.repository

import com.alejandroarcas.core.requests.DeleteUserRequest
import com.alejandroarcas.core.requests.GetUserByIdRequest
import com.alejandroarcas.core.requests.UpdateRoleRequest
import com.alejandroarcas.core.requests.UpdateUserRequest
import com.alejandroarcas.core.requests.auth.CreateUserRequest
import com.alejandroarcas.core.requests.auth.LoginUserRequest
import com.martarcas.data.mappers.toUser
import com.martarcas.data.mappers.toUserPublic
import com.martarcas.data.remote.network.UserApi
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.model.response.map
import com.martarcas.domain.model.user.User
import com.martarcas.domain.model.user.UserPublic
import com.martarcas.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
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

    override suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Unit, DataError.Remote> {
        return userApi.registerUser(createUserRequest)
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Unit, DataError.Remote> {
        return userApi.updateUser(updateUserRequest)
    }

    override suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Unit, DataError.Remote> {
        return userApi.deleteUser(deleteUserRequest)
    }

    override suspend fun login(loginRequest: LoginUserRequest): Result<User, DataError.Remote> {
        return userApi.login(loginRequest).map { userDto ->
            userDto.toUser()
        }
    }

    override suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Unit, DataError.Remote> {
        return userApi.changeRole(updateRoleRequest)
    }

}