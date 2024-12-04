package org.martarcas.usermanager.manager.data.remote.dto


import kotlinx.serialization.Serializable
import org.martarcas.usermanager.manager.domain.model.Role

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: Role
)