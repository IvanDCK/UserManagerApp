package org.martarcas.usermanager.presentation.list.model

sealed interface UpdateInfoBottomSheetActions {
    data class OnNameChange(val name: String): UpdateInfoBottomSheetActions
    data class OnSurnameChange(val surname: String): UpdateInfoBottomSheetActions
    data class OnEmailChange(val email: String): UpdateInfoBottomSheetActions
    data class OnPasswordChange(val password: String): UpdateInfoBottomSheetActions
    data object OnPasswordVisibilityChange: UpdateInfoBottomSheetActions
}