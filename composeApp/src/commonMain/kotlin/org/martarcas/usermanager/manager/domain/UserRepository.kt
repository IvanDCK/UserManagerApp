package org.martarcas.usermanager.manager.domain

import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.manager.data.dto.requests.DeleteUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.GetUserByIdRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateRoleRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.CreateUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.auth.LoginUserRequest
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.model.user.UserPublic


interface UserRepository {

    suspend fun getAllUsers(): Result<List<UserPublic>, DataError.Remote>

    suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublic, DataError.Remote>
    suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<User, DataError.Remote>

    suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Boolean, DataError.Remote>

    suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Unit, DataError.Remote>
    suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Unit, DataError.Remote>
    suspend fun login(loginRequest: LoginUserRequest): Result<Boolean, DataError.Remote>
    suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Unit, DataError.Remote>

}