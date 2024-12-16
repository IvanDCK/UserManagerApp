package org.martarcas.usermanager.data.remote.network

import org.martarcas.usermanager.data.remote.dto.ActivityLogDto
import org.martarcas.usermanager.data.remote.dto.UserDto
import org.martarcas.usermanager.data.remote.dto.UserPublicDto
import org.martarcas.usermanager.data.remote.requests.DeleteUserRequest
import org.martarcas.usermanager.data.remote.requests.GetUserByIdRequest
import org.martarcas.usermanager.data.remote.requests.UpdateRoleRequest
import org.martarcas.usermanager.data.remote.requests.UpdateUserRequest
import org.martarcas.usermanager.data.remote.requests.activity.CreateActivityLogRequest
import org.martarcas.usermanager.data.remote.requests.auth.CreateUserRequest
import org.martarcas.usermanager.data.remote.requests.auth.LoginUserRequest
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result

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