package org.martarcas.usermanager.manager.data.remote.network

import io.ktor.client.HttpClient
import org.koin.core.annotation.Single

private const val BASE_URL = "http://localhost:8080/"

class UserApiImpl(
    private val httpClient: HttpClient
): UserApi {

}