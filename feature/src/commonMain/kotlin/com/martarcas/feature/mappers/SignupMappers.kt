package com.martarcas.feature.mappers

import com.martarcas.domain.model.user.Role
import com.martarcas.domain.model.user.User
import com.martarcas.feature.presentation.signup.model.SignupUiModel

fun SignupUiModel.toDomainUser(): User {
    return User(
        id = 0,
        name = firstName,
        surname = lastName,
        email = email,
        password = password,
        role = Role.NEW_USER,
        avatarId = avatarId
    )
}