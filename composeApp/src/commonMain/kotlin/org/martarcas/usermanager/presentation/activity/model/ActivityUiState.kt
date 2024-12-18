package org.martarcas.usermanager.presentation.activity.model

import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.presentation.ui_utils.UiText

data class ActivityUiState(
    val activityList: List<ActivityUiModel> = emptyList(),
    val activityListAux: List<ActivityUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val loggedUser: User? = null,
    val filterByOwnLogs: Boolean = false,
)
