package com.alejandroarcas.core.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRoleRequest(
    val userId: Int,
    val newRole: String
)
