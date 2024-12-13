package org.martarcas.usermanager.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetUserByIdRequest(
    val id: Int
)
