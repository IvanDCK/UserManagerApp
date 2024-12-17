package org.martarcas.usermanager.presentation.profile.model

interface AvatarBottomSheetActions {
    data class OnSelectNewAvatar(val avatarId: String): AvatarBottomSheetActions
    data object OnDismissClick: AvatarBottomSheetActions
}