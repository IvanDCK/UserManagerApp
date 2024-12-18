package com.martarcas.feature.profile.model

interface DeleteDialogActions {
    data object OnConfirmClick: DeleteDialogActions
    data object OnDismissClick: DeleteDialogActions
}