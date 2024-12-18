package com.martarcas.feature.profile.model

interface AvatarBottomSheetActions {
    data class OnSelectNewAvatar(val avatarId: String): AvatarBottomSheetActions
    data object OnDismissClick: AvatarBottomSheetActions
}