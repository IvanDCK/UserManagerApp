package org.martarcas.usermanager.data.remote.requests.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserRequest(
    val email: String,
    val password: String
)
