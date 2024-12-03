package org.martarcas.usermanager.manager.presentation.signup.model

import org.martarcas.usermanager.core.presentation.UiText

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
