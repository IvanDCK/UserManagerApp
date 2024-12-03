package org.martarcas.usermanager.manager.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPublicDto (
    val id: Int,
    val name: String,
    val surname: String,
    val role: String
)