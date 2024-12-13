package org.martarcas.usermanager.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val userId: Int,
    val newName: String,
    val newSurname: String,
    val newEmail: String,
    val newPassword: String,
)