package org.martarcas.usermanager.presentation.profile.model

import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.presentation.ui_utils.UiText

data class ProfileUiState(
    val isLoading: Boolean = false,
    val loggedUser: User? = null,
    val showLogoutPopup: Boolean = false,
    val showRemoveAccountPopup: Boolean = false,
    val avatarId: String = "",
    val firstNameText: String = "",
    val lastNameText: String = "",
    val emailText: String = "",
    val passwordText: String = "",
    val isPasswordVisible: Boolean = false,
    val showAvatarChooserDialog: Boolean = false,
    val errorMessage: UiText? = null
) {
    fun updateFromUser(user: User?): ProfileUiState {
        return copy(
            loggedUser = user,
            firstNameText = user?.name ?: "",
            lastNameText = user?.surname ?: "",
            emailText = user?.email ?: "",
            passwordText = user?.password ?: "",
            avatarId = user?.avatarId ?: ""
        )
    }
}