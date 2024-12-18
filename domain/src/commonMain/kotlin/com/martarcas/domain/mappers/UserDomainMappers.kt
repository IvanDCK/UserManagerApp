package com.martarcas.domain.mappers

import com.alejandroarcas.core.requests.auth.CreateUserRequest
import com.alejandroarcas.core.requests.auth.LoginUserRequest
import com.martarcas.domain.model.user.User

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