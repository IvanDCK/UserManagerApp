package org.martarcas.usermanager.presentation.mappers

import org.martarcas.usermanager.data.remote.requests.auth.LoginUserRequest
import org.martarcas.usermanager.presentation.login.model.LoginUiModel

fun LoginUiModel.toLoginUserRequest(): LoginUserRequest {
    return LoginUserRequest(
        email = email,
        password = password
    )
}