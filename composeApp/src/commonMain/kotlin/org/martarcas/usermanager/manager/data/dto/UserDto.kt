package org.martarcas.usermanager.manager.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("surname") val surname: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("role") val role: String
)