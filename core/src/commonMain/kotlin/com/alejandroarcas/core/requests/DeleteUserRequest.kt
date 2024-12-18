package com.alejandroarcas.core.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserRequest(
    val userId: Int
)
