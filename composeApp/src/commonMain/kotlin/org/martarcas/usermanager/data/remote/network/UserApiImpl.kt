package org.martarcas.usermanager.data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
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
import org.martarcas.usermanager.data.remote.safeCall
import org.martarcas.usermanager.domain.model.response.DataError
import org.martarcas.usermanager.domain.model.response.Result

private const val BASE_URL = "http://192.168.1.19:8080"

@Single
class UserApiImpl(
    @Provided private val httpClient: HttpClient
): UserApi {
    override suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/user")
        }
    }

    override suspend fun getUserById(getUserByIdRequest: GetUserByIdRequest): Result<UserPublicDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/user/id"){
                setBody(getUserByIdRequest)
            }
        }
    }

    override suspend fun getLoggedUser(getUserByIdRequest: GetUserByIdRequest): Result<UserDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/user/id/${getUserByIdRequest.id}")
        }
    }

    override suspend fun registerUser(createUserRequest: CreateUserRequest): Result<Unit, DataError.Remote> {
        return safeCall {
            httpClient.post("$BASE_URL/user/register"){
                setBody(createUserRequest)
            }
        }
    }

    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): Result<Unit, DataError.Remote> {
        return safeCall {
            httpClient.put("$BASE_URL/user"){
                setBody(updateUserRequest)
            }
        }
    }

    override suspend fun deleteUser(deleteUserRequest: DeleteUserRequest): Result<Unit, DataError.Remote> {
        return safeCall {
            httpClient.delete("$BASE_URL/user"){
                setBody(deleteUserRequest)
            }
        }
    }

    override suspend fun login(loginRequest: LoginUserRequest): Result<UserDto, DataError.Remote> {
        return safeCall {
            httpClient.post("$BASE_URL/user/login"){
                setBody(loginRequest)
            }
        }
    }

    override suspend fun changeRole(updateRoleRequest: UpdateRoleRequest): Result<Unit, DataError.Remote> {
        return safeCall {
            httpClient.put("$BASE_URL/user/role"){
                setBody(updateRoleRequest)
            }
        }
    }

    override suspend fun createActivityLog(createActivity: CreateActivityLogRequest): Result<Unit, DataError.Remote> {
        return safeCall {
            httpClient.post("$BASE_URL/activity"){
                setBody(createActivity)
            }
        }
    }

    override suspend fun getAllActivityLogs(): Result<List<ActivityLogDto>, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/activity")
        }
    }
}