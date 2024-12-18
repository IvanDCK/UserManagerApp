package com.martarcas.domain.repository

import com.alejandroarcas.core.requests.DeleteUserRequest
import com.alejandroarcas.core.requests.GetUserByIdRequest
import com.alejandroarcas.core.requests.UpdateRoleRequest
import com.alejandroarcas.core.requests.UpdateUserRequest
import com.alejandroarcas.core.requests.auth.CreateUserRequest
import com.alejandroarcas.core.requests.auth.LoginUserRequest
import com.martarcas.domain.model.response.DataError
import com.martarcas.domain.model.response.Result
import com.martarcas.domain.model.user.User
import com.martarcas.domain.model.user.UserPublic


interface UserRepository {

    suspend fun getAllUsers(): Result<List<UserPublic>, DataError.Remote>

    suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublic, DataError.Remote>
    suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<User, DataError.Remote>

    suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Unit, DataError.Remote>

    suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Unit, DataError.Remote>
    suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Unit, DataError.Remote>
    suspend fun login(loginRequest: LoginUserRequest): Result<User, DataError.Remote>
    suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Unit, DataError.Remote>

}