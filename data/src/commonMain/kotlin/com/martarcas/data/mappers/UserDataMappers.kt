package com.martarcas.data.mappers

import com.martarcas.data.remote.dto.UserDto
import com.martarcas.data.remote.dto.UserPublicDto
import com.martarcas.domain.model.user.User
import com.martarcas.domain.model.user.UserPublic

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