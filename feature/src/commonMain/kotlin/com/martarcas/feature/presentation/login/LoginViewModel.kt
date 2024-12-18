package com.martarcas.feature.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandroarcas.core.requests.auth.LoginUserRequest
import com.martarcas.domain.model.response.onError
import com.martarcas.domain.model.response.onSuccess
import com.martarcas.domain.model.user.User
import com.martarcas.domain.use_cases.auth.LoginRequestUseCase
import com.martarcas.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import com.martarcas.feature.presentation.login.model.LoginActions
import com.martarcas.feature.presentation.login.model.LoginUiState
import com.martarcas.feature.presentation.signup.model.ValidationResult
import com.martarcas.feature.presentation.ui_utils.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class LoginViewModel(
    private val loginRequestUseCase: LoginRequestUseCase,
    @Provided private val saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Function to update the login screen state
     * @param update
     */
    private fun updateState(update: LoginUiState.() -> LoginUiState) {
        _uiState.update { it.update() }
    }

    /**
     * Function to handle all the login screen actions
     * @param action
     */
    fun onAction(action: LoginActions) {
        when (action) {
            is LoginActions.OnEmailChange -> updateState { copy(email = action.value) }
            is LoginActions.OnPasswordChange -> updateState { copy(password = action.value) }
            is LoginActions.OnPasswordVisibleClick -> updateState { copy(isPasswordVisible = !isPasswordVisible) }
            is LoginActions.OnRememberMeChange -> updateState { copy(rememberMeIsChecked = !rememberMeIsChecked) }
            is LoginActions.OnLoginButtonClick -> handleLogin()
            is LoginActions.OnNotHaveAccountClick -> updateState { copy(shouldNavigateToSignup = true) }
        }
    }

    /**
     * Function to handle the login button click
     */
    private fun handleLogin() {
        updateState { copy(isLoadingOnClick = true) }
        val email = _uiState.value.email
        val password = _uiState.value.password

        val validationResult = validateLoginInputs(email, password)
        if (validationResult.isValid) {
            viewModelScope.launch {

                val loginUserRequest = LoginUserRequest(email, password)

                loginRequestUseCase(loginUserRequest).onSuccess { userResponse ->
                    if (uiState.value.rememberMeIsChecked) {
                        saveRememberMeAndUser(true, userResponse)
                    } else {
                        saveRememberMeAndUser(false, userResponse)
                    }
                    updateState { copy(shouldNavigateToList = true) }
                }.onError { error ->
                    updateState { copy(errorMessage = error.toUiText()) }
                    resetLoadingProgress()
                }
            }
        } else {
            updateState { copy(validationErrors = validationResult.errors) }
            resetLoadingProgress()
        }
    }

    /**
     * Function to validate the login inputs
     * @param email
     * @param password
     */
    private fun validateLoginInputs(email: String, password: String): ValidationResult {
        val errors = mutableListOf<String>()
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        if (email.isBlank() || !email.matches(emailRegex)) errors.add("The email is not valid.")
        if (password.isBlank()) errors.add("The password cannot be empty.")

        return ValidationResult(
            errors.isEmpty(),
            errors
        )
    }

    /**
     * Function to reset the loading progress to false
     */
    private fun resetLoadingProgress() {
        updateState { copy(isLoadingOnClick = false) }
    }

    /**
     * Function to navigate to the signup screen
     */
    fun onNavigatedToSignup() {
        updateState { copy(shouldNavigateToSignup = false) }
    }

    /**
     * Function to navigate to the list screen
     */
    fun onNavigatedToList() {
        updateState { copy(shouldNavigateToList = false) }
    }

    /**
     * Function to save the remember me and user in the data store
     */
    private suspend fun saveRememberMeAndUser(rememberMe: Boolean, userResponse: User) {
        saveRememberMeAndUserUseCase(
            rememberMe = rememberMe,
            user = userResponse.copy()
        )
    }
}
