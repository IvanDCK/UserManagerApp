package org.martarcas.usermanager.presentation.activity.model

import org.martarcas.usermanager.presentation.ui_utils.UiText

data class ActivityUiState(
    val activityList: List<ActivityUiModel> = emptyList(),
    val timeSortAscending: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)
