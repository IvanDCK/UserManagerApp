package org.martarcas.usermanager.data.remote.dto

import kotlinx.serialization.Serializable
import org.martarcas.usermanager.domain.model.user.Role

@Serializable
data class UserPublicDto (
    val id: Int,
    val name: String,
    val surname: String,
    val role: Role,
    val avatarId: String,
)