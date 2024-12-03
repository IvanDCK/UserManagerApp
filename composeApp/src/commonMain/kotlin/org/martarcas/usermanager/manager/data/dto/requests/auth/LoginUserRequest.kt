package org.martarcas.usermanager.manager.data.dto.requests.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserRequest(
    val email: String,
    val password: String
)
