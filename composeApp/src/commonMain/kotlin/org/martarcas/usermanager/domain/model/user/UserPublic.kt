package org.martarcas.usermanager.domain.model.user

data class UserPublic(
    val id: Int,
    val name: String,
    val surname: String,
    val role: Role
)
