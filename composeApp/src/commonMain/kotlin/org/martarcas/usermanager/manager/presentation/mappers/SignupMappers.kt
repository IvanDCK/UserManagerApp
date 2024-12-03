package org.martarcas.usermanager.manager.presentation.mappers

import org.martarcas.usermanager.manager.data.dto.requests.auth.CreateUserRequest
import org.martarcas.usermanager.manager.presentation.signup.model.SignupUiModel

fun SignupUiModel.toCreateUserRequest(): CreateUserRequest {
    return CreateUserRequest(
        name = firstName,
        surname = lastName,
        email = email,
        password = password
    )
}