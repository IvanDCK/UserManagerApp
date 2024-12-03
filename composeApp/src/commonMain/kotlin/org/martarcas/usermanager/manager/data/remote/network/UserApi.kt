package org.martarcas.usermanager.manager.data.remote.network

import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.manager.data.dto.UserDto
import org.martarcas.usermanager.manager.data.dto.UserPublicDto
import org.martarcas.usermanager.manager.data.dto.requests.DeleteUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.GetUserByIdRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateRoleRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.CreateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.LoginUserRequest

interface UserApi {

    suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote>

    suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublicDto, DataError.Remote>
    suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<UserDto, DataError.Remote>

    suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Boolean, DataError.Remote>

    suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Boolean, DataError.Remote>
    suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Boolean, DataError.Remote>
    suspend fun login(loginRequest: LoginUserRequest): Result<Boolean, DataError.Remote>
    suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Boolean, DataError.Remote>

}