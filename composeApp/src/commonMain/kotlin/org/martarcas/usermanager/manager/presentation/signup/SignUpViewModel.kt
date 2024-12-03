package org.martarcas.usermanager.manager.presentation.signup

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
import org.martarcas.usermanager.manager.domain.use_cases.auth.SignUpRequestUseCase
import org.martarcas.usermanager.manager.presentation.mappers.toCreateUserRequest
import org.martarcas.usermanager.manager.presentation.signup.model.SignupActions
import org.martarcas.usermanager.manager.presentation.signup.model.SignupUiModel
import org.martarcas.usermanager.manager.presentation.signup.model.SignupUiState
import org.martarcas.usermanager.manager.presentation.signup.model.ValidationResult

@KoinViewModel
class SignUpViewModel(
    private val signUpRequestUseCase: SignUpRequestUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: SignupActions) {
        when (action) {
            is SignupActions.OnEmailChange -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(email = action.value)
                }
            }

            is SignupActions.OnFirstNameChange -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(firstName = action.value)
                }
            }

            is SignupActions.OnLastNameChange -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(lastName = action.value)
                }
            }

            is SignupActions.OnPasswordChange -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(password = action.value)
                }
            }

            is SignupActions.OnPasswordVisibleClick -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
                }
            }

            is SignupActions.OnSignupButtonClick -> {

                _uiState.update { uiStateValue ->
                    uiStateValue.copy(isLoadingOnClick = true)
                }

                val validationResult = validateSignupInputs(
                    name = _uiState.value.firstName,
                    surname = _uiState.value.lastName,
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )

                if (validationResult.isValid) {
                    viewModelScope.launch {
                        val userModel = SignupUiModel(
                            firstName = _uiState.value.firstName,
                            lastName = _uiState.value.lastName,
                            email = _uiState.value.email,
                            password = _uiState.value.password
                        ).toCreateUserRequest()

                        val response = signUpRequestUseCase(userModel)

                        response.onSuccess {
                            _uiState.update { uiStateValue ->
                                uiStateValue.copy(shouldNavigateToLogin = true)
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

            is SignupActions.OnAlreadyHaveAccountClick -> {
                _uiState.update { uiStateValue ->
                    uiStateValue.copy(shouldNavigateToLogin = true)
                }
            }
        }
    }

    private fun validateSignupInputs(
        name: String,
        surname: String,
        email: String,
        password: String
    ): ValidationResult {
        val errors = mutableListOf<String>()

        if (name.isBlank()) errors.add("The name cannot be empty.")

        if (surname.isBlank()) errors.add("The surname cannot be empty.")

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (email.isBlank() || !email.matches(emailRegex)) {
            errors.add("The email is not valid.")
        }

        if (password.isBlank()) {
            errors.add("The password cannot be empty.")
        } else if (password.length < 6) {
            errors.add("The password must have at least 6 characters.")
        }

        return ValidationResult(errors.isEmpty(), errors)
    }

    fun onNavigatedToLogin(){
        _uiState.update { uiStateValue ->
            uiStateValue.copy(shouldNavigateToLogin = false)
        }
        resetLoadingProgress()
    }

    private fun resetLoadingProgress(){
        _uiState.update { uiStateValue ->
            uiStateValue.copy(isLoadingOnClick = false)
        }
    }
}