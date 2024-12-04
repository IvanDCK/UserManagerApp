package org.martarcas.usermanager.manager.data.remote.dto

import kotlinx.serialization.Serializable
import org.martarcas.usermanager.manager.domain.model.Role

@Serializable
data class UserPublicDto (
    val id: Int,
    val name: String,
    val surname: String,
    val role: Role
)