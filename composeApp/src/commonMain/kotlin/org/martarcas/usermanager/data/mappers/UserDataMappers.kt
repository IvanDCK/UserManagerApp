package org.martarcas.usermanager.data.mappers

import org.martarcas.usermanager.data.remote.dto.UserDto
import org.martarcas.usermanager.data.remote.dto.UserPublicDto
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.model.user.UserPublic

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        surname = surname,
        email = email,
        password = password,
        role = role,
        avatarId = avatarId
    )
}

fun UserPublicDto.toUserPublic(): UserPublic {
    return UserPublic(
        id = id,
        name = name,
        surname = surname,
        role = role,
        avatarId = avatarId
    )
}