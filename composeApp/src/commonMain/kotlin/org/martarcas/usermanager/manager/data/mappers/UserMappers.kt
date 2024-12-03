package org.martarcas.usermanager.manager.data.mappers

import org.martarcas.usermanager.manager.data.dto.UserDto
import org.martarcas.usermanager.manager.data.dto.UserPublicDto
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.model.user.UserPublic

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        surname = surname,
        email = email,
        password = password,
        role = role
    )
}

fun UserPublicDto.toUserPublic(): UserPublic {
    return UserPublic(
        id = id,
        name = name,
        surname = surname,
        role = role
    )
}