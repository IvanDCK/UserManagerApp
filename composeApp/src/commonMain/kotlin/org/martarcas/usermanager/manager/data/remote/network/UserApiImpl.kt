package org.martarcas.usermanager.manager.data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.martarcas.usermanager.core.data.safeCall
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

private const val BASE_URL = "http://10.0.2.2:8080/user"

@Single
class UserApiImpl(
    @Provided private val httpClient: HttpClient
): UserApi {
    override suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote> {
        return safeCall {
            httpClient.get(BASE_URL)
        }
    }

    override suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublicDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${BASE_URL}/id"){
                setBody(getUserByIdRequest)
            }
        }
    }

    override suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<UserDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${BASE_URL}/id"){
                setBody(getUserByIdRequest)
            }
        }
    }

    override suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Boolean, DataError.Remote> {
        return safeCall {
            httpClient.post("${BASE_URL}/register"){
                setBody(createUserRequest)
            }
        }
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Boolean, DataError.Remote> {
        return safeCall {
            httpClient.put(BASE_URL){
                setBody(updateUserRequest)
            }
        }
    }

    override suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Boolean, DataError.Remote> {
        return safeCall {
            httpClient.delete(BASE_URL){
                setBody(deleteUserRequest)
            }
        }
    }

    override suspend fun login(loginRequest: LoginUserRequest): Result<Boolean, DataError.Remote> {
        return safeCall {
            httpClient.post("${BASE_URL}/login"){
                setBody(loginRequest)
            }
        }
    }

    override suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Boolean, DataError.Remote> {
        return safeCall {
            httpClient.put("${BASE_URL}/role"){
                setBody(updateRoleRequest)
            }
        }
    }

}