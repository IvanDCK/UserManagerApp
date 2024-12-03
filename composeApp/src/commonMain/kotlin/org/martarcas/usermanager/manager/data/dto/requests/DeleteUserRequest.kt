package org.martarcas.usermanager.manager.data.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserRequest(
    val userId: Int
)
