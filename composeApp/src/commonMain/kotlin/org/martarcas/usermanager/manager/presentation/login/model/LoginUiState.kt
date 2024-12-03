package org.martarcas.usermanager.manager.presentation.login.model

import org.martarcas.usermanager.core.presentation.UiText

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val shouldNavigateToSignup: Boolean = false,
    val shouldNavigateToList: Boolean = false,
    val errorMessage: UiText? = null,
    val validationErrors: List<String> = emptyList(),
    val isLoadingOnClick: Boolean = false
)
