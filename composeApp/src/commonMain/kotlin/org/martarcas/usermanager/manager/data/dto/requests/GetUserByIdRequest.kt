package org.martarcas.usermanager.manager.data.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetUserByIdRequest(
    val id: Int
)
