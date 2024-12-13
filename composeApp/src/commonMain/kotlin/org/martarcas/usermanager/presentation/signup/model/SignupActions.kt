package org.martarcas.usermanager.presentation.signup.model

sealed interface SignupActions {
    data class OnFirstNameChange(val value: String): SignupActions
    data class OnLastNameChange(val value: String): SignupActions
    data class OnEmailChange(val value: String): SignupActions
    data class OnPasswordChange(val value: String): SignupActions
    data object OnPasswordVisibleClick: SignupActions
    data object OnSignupButtonClick: SignupActions
    data object OnAlreadyHaveAccountClick: SignupActions
}