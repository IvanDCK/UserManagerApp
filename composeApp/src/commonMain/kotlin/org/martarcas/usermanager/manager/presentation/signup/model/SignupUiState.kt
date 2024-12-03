package org.martarcas.usermanager.manager.presentation.signup.model

data class SignupUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val validationErrors: List<String> = emptyList(),
    val isPasswordVisible: Boolean = false,
    val isSignupButtonEnabled: Boolean = false,
    val shouldNavigateToLogin: Boolean = false
)
