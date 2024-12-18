package com.martarcas.feature.presentation.login.model

interface LoginActions {
    data class OnEmailChange(val value: String): LoginActions
    data class OnPasswordChange(val value: String): LoginActions
    data object OnRememberMeChange: LoginActions
    data object OnLoginButtonClick: LoginActions
    data object OnNotHaveAccountClick: LoginActions
    data object OnPasswordVisibleClick: LoginActions
}