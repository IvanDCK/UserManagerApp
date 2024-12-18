package com.martarcas.feature.signup.model

import com.martarcas.feature.ui_utils.UiText

data class SignupUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val avatarId: String = "user_avatar0",
    val validationErrors: List<String> = emptyList(),
    val isPasswordVisible: Boolean = false,
    val isSignupButtonEnabled: Boolean = false,
    val shouldNavigateToLogin: Boolean = false,
    val errorMessage: UiText? = null,
    val isLoadingOnClick: Boolean = false
)
