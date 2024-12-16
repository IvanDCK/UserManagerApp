package org.martarcas.usermanager.presentation.profile.model

interface AvatarDialogActions {
    data class OnSelectNewAvatar(val avatarId: String): AvatarDialogActions
    data object OnDismissClick: AvatarDialogActions
}