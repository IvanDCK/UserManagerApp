package org.martarcas.usermanager.manager.domain.model.user

import org.martarcas.usermanager.manager.domain.model.Role

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: Role
)

