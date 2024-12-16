package org.martarcas.usermanager.presentation.mappers

import org.martarcas.usermanager.domain.model.user.Role
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.presentation.login.model.LoginUiModel

fun LoginUiModel.toDomainUser(): User {
    return User(
        id = 0,
        name = "",
        surname = "",
        email = email,
        password = password,
        role = Role.NEW_USER,
        avatarId = "user_avatar0"
    )
}