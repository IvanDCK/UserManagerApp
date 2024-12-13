package org.martarcas.usermanager.presentation.mappers

import org.martarcas.usermanager.data.remote.requests.auth.CreateUserRequest
import org.martarcas.usermanager.presentation.signup.model.SignupUiModel

fun SignupUiModel.toCreateUserRequest(): CreateUserRequest {
    return CreateUserRequest(
        name = firstName,
        surname = lastName,
        email = email,
        password = password
    )
}