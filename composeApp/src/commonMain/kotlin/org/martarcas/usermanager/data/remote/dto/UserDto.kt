package org.martarcas.usermanager.data.remote.dto


import kotlinx.serialization.Serializable
import org.martarcas.usermanager.domain.model.user.Role

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: Role
)