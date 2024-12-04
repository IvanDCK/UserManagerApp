package org.martarcas.usermanager.manager.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.martarcas.usermanager.core.domain.onError
import org.martarcas.usermanager.core.domain.onSuccess
import org.martarcas.usermanager.core.presentation.toUiText
import org.martarcas.usermanager.manager.data.dto.UserDto
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.manager.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import org.martarcas.usermanager.manager.presentation.login.model.LoginUiModel
import org.martarcas.usermanager.manager.presentation.login.model.LoginUiState
import org.martarcas.usermanager.manager.presentation.mappers.toLoginUserRequest
import org.martarcas.usermanager.manager.presentation.signup.model.ValidationResult

@KoinViewModel
class LoginViewModel(
    private val loginRequestUseCase: LoginRequestUseCase,
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: LoginActions) {
        when (action) {
            is LoginActions.OnEmailChange -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(email = action.value)
                }
            }

            is LoginActions.OnPasswordChange -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(password = action.value)
                }
            }

            is LoginActions.OnLoginButtonClick -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(isLoadingOnClick = true)
                }

                val validationResult = validateLoginInputs(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )

                if (validationResult.isValid) {
                    viewModelScope.launch {
                        val model = LoginUiModel(
                            email = _uiState.value.email,
                            password = _uiState.value.password
                        ).toLoginUserRequest()

                        val response = loginRequestUseCase(model)

                        response.onSuccess { userResponse ->

                            println("USE RESPONSE: $userResponse")

                            if (_uiState.value.rememberMeIsChecked) {
                                saveRememberMeAndUser(userResponse)
                            }

                            _uiState.update { uiStateValue ->
                                uiStateValue.copy(shouldNavigateToList = true)
                            }
                        }.onError { error ->
                            _uiState.update { uiStateValue ->
                                uiStateValue.copy(errorMessage = error.toUiText())
                            }
                            resetLoadingProgress()
                        }
                    }
                } else {
                    _uiState.update { uiStateValue ->
                        uiStateValue.copy(validationErrors = validationResult.errors)
                    }
                    resetLoadingProgress()
                }

            }

            is LoginActions.OnNotHaveAccountClick -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(shouldNavigateToSignup = true)
                }
            }

            is LoginActions.OnPasswordVisibleClick -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
                }
            }

            is LoginActions.OnRememberMeChange -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(rememberMeIsChecked = !_uiState.value.rememberMeIsChecked)
                }
            }
        }
    }

    private fun validateLoginInputs(
        email: String,
        password: String
    ): ValidationResult {
        val errors = mutableListOf<String>()

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (email.isBlank() || !email.matches(emailRegex)) {
            errors.add("The email is not valid.")
        }
        if (password.isBlank()) errors.add("The password cannot be empty.")

        return ValidationResult(errors.isEmpty(), errors)
    }

    private fun resetLoadingProgress() {
        _uiState.update { uiStateValue ->
            uiStateValue.copy(isLoadingOnClick = false)
        }
    }

    fun onNavigatedToSignup() {
        _uiState.update { uiStateValue ->
            uiStateValue.copy(shouldNavigateToSignup = false)
        }
    }

    fun onNavigatedToList() {
        _uiState.update { uiStateValue ->
            uiStateValue.copy(shouldNavigateToList = false)
        }
    }

    private suspend fun saveRememberMeAndUser(userResponse: UserDto) {
        dataStoreUseCases.saveRememberMeAndUserUseCase(
            true, User(
                id = userResponse.id,
                name = userResponse.name,
                surname = userResponse.surname,
                email = userResponse.email,
                password = userResponse.password,
                role = userResponse.role
            )
        )
    }
}