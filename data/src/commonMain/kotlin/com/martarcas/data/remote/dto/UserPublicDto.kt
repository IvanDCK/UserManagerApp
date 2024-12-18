package com.martarcas.data.remote.dto

import com.martarcas.domain.model.user.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserPublicDto (
    val id: Int,
    val name: String,
    val surname: String,
    val role: Role,
    val avatarId: String,
)