package org.martarcas.usermanager.presentation.mappers

import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.presentation.signup.model.SignupUiModel

fun SignupUiModel.toDomainUser(): User {
    return User(
        id = 0,
        name = firstName,
        surname = lastName,
        email = email,
        password = password,
        role = org.martarcas.usermanager.domain.model.user.Role.NEW_USER
    )
}