package com.martarcas.feature.presentation.profile.model

interface AvatarBottomSheetActions {
    data class OnSelectNewAvatar(val avatarId: String): AvatarBottomSheetActions
    data object OnDismissClick: AvatarBottomSheetActions
}