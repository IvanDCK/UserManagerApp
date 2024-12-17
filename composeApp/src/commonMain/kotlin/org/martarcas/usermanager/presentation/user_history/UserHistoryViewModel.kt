package org.martarcas.usermanager.presentation.user_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.martarcas.usermanager.domain.model.response.onError
import org.martarcas.usermanager.domain.model.response.onSuccess
import org.martarcas.usermanager.domain.use_cases.activity.GetAllActivityLogsUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.presentation.mappers.toActivityUiModel
import org.martarcas.usermanager.presentation.ui_utils.toUiText
import org.martarcas.usermanager.presentation.user_history.model.UserHistoryUiState

@KoinViewModel
class UserHistoryViewModel(
    private val getAllActivityLogsUseCase: GetAllActivityLogsUseCase,
    @Provided private val readUserUseCase: ReadUserUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UserHistoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getLoggedUser()
    }

    private fun getAllActivityLogs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = getAllActivityLogsUseCase()
            result
                .onSuccess { logs ->

                    val loggedUserName ="${uiState.value.loggedUser?.name} ${uiState.value.loggedUser?.surname}"
                    val userLogs = logs.filter { it.userName == loggedUserName || it.targetUserName == loggedUserName }

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            activityList = userLogs.map { it.toActivityUiModel() },
                            memberSince = formatTimestamp(
                                userLogs.firstOrNull {
                                    it.activityType == "CreatedUser"
                                }
                                    ?.actionTimestamp?.toLong()?.div(1000) ?: 0
                            )
                        )
                    }

                }
                .onError { error ->
                    _uiState.update {
                        it.copy(
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
    }


    private fun formatTimestamp(timestampInSeconds: Long): String {
        val instant = Instant.fromEpochSeconds(timestampInSeconds)

        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
        val month = localDateTime.monthNumber.toString().padStart(2, '0')
        val year = localDateTime.year.toString()

        return "$day/$month/$year"
    }

    private fun getLoggedUser() {
        viewModelScope.launch {
            readUserUseCase.invoke().collect { user ->
                _uiState.update {
                    it.copy(
                        loggedUser = user
                    )
                }
                getAllActivityLogs()
            }
        }
    }
}