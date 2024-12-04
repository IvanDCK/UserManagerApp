package org.martarcas.usermanager.manager.presentation.mappers

import org.martarcas.usermanager.manager.data.remote.requests.auth.LoginUserRequest
import org.martarcas.usermanager.manager.presentation.login.model.LoginUiModel

fun LoginUiModel.toLoginUserRequest(): LoginUserRequest {
    return LoginUserRequest(
        email = email,
        password = password
    )
}