package com.martarcas.feature.presentation.activity.model

import com.martarcas.domain.model.user.User
import com.martarcas.feature.presentation.ui_utils.UiText

data class ActivityUiState(
    val activityList: List<ActivityUiModel> = emptyList(),
    val activityListAux: List<ActivityUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val loggedUser: User? = null,
    val filterByOwnLogs: Boolean = false,
)
