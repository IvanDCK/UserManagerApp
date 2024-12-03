package org.martarcas.usermanager.manager.data.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.martarcas.usermanager.core.data.safeCall
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.manager.data.dto.UserPublicDto

private const val BASE_URL = "http://10.0.2.2:8080/user"

class UserApiImpl(
    private val httpClient: HttpClient
): UserApi {
    override suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote> {
        return safeCall {
            httpClient.get(BASE_URL)
        }
    }

}