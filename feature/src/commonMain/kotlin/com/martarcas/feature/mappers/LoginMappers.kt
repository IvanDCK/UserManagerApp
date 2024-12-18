package com.martarcas.feature.mappers

import com.martarcas.domain.model.user.Role
import com.martarcas.domain.model.user.User
import com.martarcas.feature.login.model.LoginUiModel

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