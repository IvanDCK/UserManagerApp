package org.martarcas.usermanager.presentation.user_history.model

import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.presentation.activity.model.ActivityUiModel
import org.martarcas.usermanager.presentation.ui_utils.UiText

data class UserHistoryUiState(
    val activityList: List<ActivityUiModel> = emptyList(),
    val loggedUser: User? = null,
    val timeSortAscending: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val memberSince: String = ""
)
