package org.martarcas.usermanager.presentation.signup.model

import org.martarcas.usermanager.presentation.ui_utils.UiText

data class SignupUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val validationErrors: List<String> = emptyList(),
    val isPasswordVisible: Boolean = false,
    val isSignupButtonEnabled: Boolean = false,
    val shouldNavigateToLogin: Boolean = false,
    val errorMessage: UiText? = null,
    val isLoadingOnClick: Boolean = false
)
