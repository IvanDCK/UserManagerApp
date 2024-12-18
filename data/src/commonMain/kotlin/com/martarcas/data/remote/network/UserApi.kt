package com.martarcas.data.remote.network

import com.alejandroarcas.core.requests.DeleteUserRequest
import com.alejandroarcas.core.requests.GetUserByIdRequest
import com.alejandroarcas.core.requests.UpdateRoleRequest
import com.alejandroarcas.core.requests.UpdateUserRequest
import com.alejandroarcas.core.requests.activity.CreateActivityLogRequest
import com.alejandroarcas.core.requests.auth.CreateUserRequest
import com.alejandroarcas.core.requests.auth.LoginUserRequest
import com.martarcas.data.remote.dto.ActivityLogDto
import com.martarcas.data.remote.dto.UserDto
import com.martarcas.data.remote.dto.UserPublicDto
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result

interface UserApi {

    suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote>

    suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublicDto, DataError.Remote>
    suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<UserDto, DataError.Remote>

    suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Unit, DataError.Remote>

    suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Unit, DataError.Remote>
    suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Unit, DataError.Remote>
    suspend fun login(loginRequest: LoginUserRequest): Result<UserDto, DataError.Remote>
    suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Unit, DataError.Remote>

    // Activity Logs

    suspend fun createActivityLog(createActivity: CreateActivityLogRequest): Result<Unit, DataError.Remote>
    suspend fun getAllActivityLogs(): Result<List<ActivityLogDto>, DataError.Remote>
}