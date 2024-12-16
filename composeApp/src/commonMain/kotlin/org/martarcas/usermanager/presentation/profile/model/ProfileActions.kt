package org.martarcas.usermanager.presentation.profile.model

interface ProfileActions {
    data object OnLogoutButtonClick: ProfileActions
    data object OnEditAvatarButtonClick: ProfileActions
    data class OnFirstNameChange(val value: String): ProfileActions
    data class OnLastNameChange(val value: String): ProfileActions
    data class OnEmailChange(val value: String): ProfileActions
    data class OnPasswordChange(val value: String): ProfileActions
    data object OnPasswordVisibleClick: ProfileActions
    data object OnSaveButtonClick: ProfileActions
    data object OnRemoveAccountButtonClick: ProfileActions
}