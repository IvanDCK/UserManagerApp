package org.martarcas.usermanager.presentation.login.model

import org.martarcas.usermanager.presentation.ui_utils.UiText

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val shouldNavigateToSignup: Boolean = false,
    val shouldNavigateToList: Boolean = false,
    val errorMessage: UiText? = null,
    val validationErrors: List<String> = emptyList(),
    val isLoadingOnClick: Boolean = false,
    val rememberMeIsChecked: Boolean = false
)
