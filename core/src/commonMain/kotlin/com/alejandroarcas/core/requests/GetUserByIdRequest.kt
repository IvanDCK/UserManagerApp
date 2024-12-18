package com.alejandroarcas.core.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetUserByIdRequest(
    val id: Int
)
