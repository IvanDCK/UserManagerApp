package org.martarcas.usermanager.presentation.profile.model

interface DeleteDialogActions {
    data object OnConfirmClick: DeleteDialogActions
    data object OnDismissClick: DeleteDialogActions
}