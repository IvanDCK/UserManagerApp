package org.martarcas.usermanager.manager.domain.model.user

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String
)

