package com.martarcas.feature.presentation.user_history.model

import com.martarcas.domain.model.user.User
import com.martarcas.feature.presentation.activity.model.ActivityUiModel
import com.martarcas.feature.presentation.ui_utils.UiText

data class UserHistoryUiState(
    val activityList: List<ActivityUiModel> = emptyList(),
    val loggedUser: User? = null,
    val timeSortAscending: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val memberSince: String = ""
)
