package org.martarcas.usermanager.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.martarcas.usermanager.domain.model.response.onError
import org.martarcas.usermanager.domain.model.response.onSuccess
import org.martarcas.usermanager.domain.use_cases.activity.GetAllActivityLogsUseCase
import org.martarcas.usermanager.presentation.activity.model.ActivityActions
import org.martarcas.usermanager.presentation.activity.model.ActivityUiState
import org.martarcas.usermanager.presentation.mappers.toActivityUiModel
import org.martarcas.usermanager.presentation.ui_utils.toUiText

@KoinViewModel
class ActivityViewModel(
    private val getAllActivityLogsUseCase: GetAllActivityLogsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllActivityLogs()
    }

    private fun getAllActivityLogs() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = getAllActivityLogsUseCase()
            result
                .onSuccess { logs ->
                    updateState {
                        copy(
                            isLoading = false,
                            activityList = logs.map { it.toActivityUiModel() }
                        )
                    }

                }
                .onError { error ->
                    updateState { copy(errorMessage = error.toUiText()) }
                }
        }
    }


    fun onAction(action: ActivityActions) {
        when (action) {
            is ActivityActions.OnSortByTimeButtonClick -> {
                updateState { copy(timeSortAscending = !timeSortAscending) }
            }
        }
    }

    private fun updateState(update: ActivityUiState.() -> ActivityUiState) {
        _uiState.update { it.update() }
    }


}