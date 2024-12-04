package org.martarcas.usermanager.manager.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserRequest(
    val userId: Int
)
