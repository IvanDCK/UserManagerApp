package com.martarcas.feature.presentation.profile.model

interface DeleteDialogActions {
    data object OnConfirmClick: DeleteDialogActions
    data object OnDismissClick: DeleteDialogActions
}