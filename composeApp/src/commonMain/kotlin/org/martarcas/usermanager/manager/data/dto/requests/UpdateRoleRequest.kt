package org.martarcas.usermanager.manager.data.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRoleRequest(
    val userId: Int,
    val newRole: String
)
