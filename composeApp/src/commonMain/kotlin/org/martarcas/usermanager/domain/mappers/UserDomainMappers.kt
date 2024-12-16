package org.martarcas.usermanager.domain.mappers

import org.martarcas.usermanager.data.remote.requests.auth.CreateUserRequest
import org.martarcas.usermanager.data.remote.requests.auth.LoginUserRequest
import org.martarcas.usermanager.domain.model.user.User

fun User.toLoginUserRequest(): LoginUserRequest {
    return LoginUserRequest(
        email = email,
        password = password
    )
}

fun User.toCreateUserRequest(): CreateUserRequest {
    return CreateUserRequest(
        name = name,
        surname = surname,
        email = email,
        password = password,
        avatarId = avatarId
    )
}