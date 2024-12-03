package org.martarcas.usermanager.manager.domain.model.user

import org.martarcas.usermanager.manager.domain.model.Role

data class UserPublic(
    val id: Int,
    val name: String,
    val surname: String,
    val role: Role
)
