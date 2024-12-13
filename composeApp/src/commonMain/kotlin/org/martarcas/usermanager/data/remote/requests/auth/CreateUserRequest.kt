package org.martarcas.usermanager.data.remote.requests.auth

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
