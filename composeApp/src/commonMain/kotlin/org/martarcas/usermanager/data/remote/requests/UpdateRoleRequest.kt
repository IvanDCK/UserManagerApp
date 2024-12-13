package org.martarcas.usermanager.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRoleRequest(
    val userId: Int,
    val newRole: String
)
