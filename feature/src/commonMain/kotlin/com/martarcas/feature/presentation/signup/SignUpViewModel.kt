package com.martarcas.feature.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandroarcas.core.requests.activity.CreateActivityLogRequest
import com.alejandroarcas.core.requests.auth.CreateUserRequest
import com.martarcas.domain.model.response.onError
import com.martarcas.domain.model.response.onSuccess
import com.martarcas.domain.use_cases.activity.CreateActivityLogUseCase
import com.martarcas.domain.use_cases.auth.SignUpRequestUseCase
import com.martarcas.feature.presentation.signup.model.SignupActions
import com.martarcas.feature.presentation.signup.model.SignupUiState
import com.martarcas.feature.presentation.signup.model.ValidationResult
import com.martarcas.feature.presentation.ui_utils.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignUpViewModel(
    private val signUpRequestUseCase: SignUpRequestUseCase,
    private val createActivityLogUseCase: CreateActivityLogUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Function to update the signup screen state
     * @param update
     */
    private fun updateField(update: SignupUiState.() -> SignupUiState) {
        _uiState.update { it.update() }
    }

    /**
     * Function to handle all the signup screen actions
     * @param action
     */
    fun onAction(action: SignupActions) {
        when (action) {
            is SignupActions.OnEmailChange -> updateField { copy(email = action.value) }
            is SignupActions.OnFirstNameChange -> updateField { copy(firstName = action.value) }
            is SignupActions.OnLastNameChange -> updateField { copy(lastName = action.value) }
            is SignupActions.OnPasswordChange -> updateField { copy(password = action.value) }
            is SignupActions.OnPasswordVisibleClick -> updateField { copy(isPasswordVisible = !isPasswordVisible) }
            is SignupActions.OnSignupButtonClick -> handleSignup()
            is SignupActions.OnAlreadyHaveAccountClick -> updateField { copy(shouldNavigateToLogin = true) }
        }
    }

    /**
     * Function to handle the signup button click
     */
    private fun handleSignup() {
        updateField { copy(isLoadingOnClick = true) }

        val validationResult = validateSignupInputs(
            name = _uiState.value.firstName,
            surname = _uiState.value.lastName,
            email = _uiState.value.email,
            password = _uiState.value.password,

        )

        if (validationResult.isValid) {
            val firstName = _uiState.value.firstName
            val lastName = _uiState.value.lastName
            viewModelScope.launch {
                val userModel = CreateUserRequest(
                    name = firstName,
                    surname = lastName,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    avatarId = _uiState.value.avatarId
                )

                val response = signUpRequestUseCase(userModel)

                response.onSuccess {
                    createActivityLog(
                        CreateActivityLogRequest(
                            "$firstName $lastName",
                            "CreatedUser",
                            "none",
                            "none"
                        )
                    )
                    updateField { copy(shouldNavigateToLogin = true) }

                }.onError { error ->
                    updateField { copy(errorMessage = error.toUiText()) }
                    resetLoadingProgress()
                }
            }
        } else {
            updateField { copy(validationErrors = validationResult.errors) }
            resetLoadingProgress()
        }
    }

    /**
     * Function to validate the signup inputs
     * @param name
     * @param surname
     * @param email
     * @param password
     */
    private fun validateSignupInputs(
        name: String,
        surname: String,
        email: String,
        password: String,
    ): ValidationResult {
        val errors = mutableListOf<String>()

        if (name.isBlank()) errors.add("The name cannot be empty.")
        if (surname.isBlank()) errors.add("The surname cannot be empty.")
        if (!email.matches(Regex("^([A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"))) {
            errors.add("The email is not valid.")
        }
        if (password.isBlank()) errors.add("The password cannot be empty.")
        if (password.length < 6) errors.add("The password must have at least 6 characters.")

        return ValidationResult(
            errors.isEmpty(),
            errors
        )
    }

    /**
     * Function to navigate to the login screen
     */
    fun onNavigatedToLogin() {
        updateField { copy(shouldNavigateToLogin = false) }
        resetLoadingProgress()
    }

    /**
     * Function to reset the loading progress to false
     */
    private fun resetLoadingProgress() {
        updateField { copy(isLoadingOnClick = false) }
    }

    private fun createActivityLog(activityLog: CreateActivityLogRequest) {
        viewModelScope.launch {
            createActivityLogUseCase(activityLog)
        }
    }

    private fun getCurrentTimestamp(): String {
        val currentInstant: Instant = Clock.System.now()
        return currentInstant.toEpochMilliseconds().toString()
    }


}