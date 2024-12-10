package org.martarcas.usermanager.manager.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.martarcas.usermanager.core.domain.model.onError
import org.martarcas.usermanager.core.domain.model.onSuccess
import org.martarcas.usermanager.core.presentation.toUiText
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import org.martarcas.usermanager.manager.presentation.login.model.LoginUiModel
import org.martarcas.usermanager.manager.presentation.login.model.LoginUiState
import org.martarcas.usermanager.manager.presentation.mappers.toLoginUserRequest
import org.martarcas.usermanager.manager.presentation.signup.model.ValidationResult

@KoinViewModel
class LoginViewModel(
    private val loginRequestUseCase: LoginRequestUseCase,
    @Provided private val dataStoreUseCases: DataStoreUseCases
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

        val validationResult = validateLoginInputs(uiState.value.email, uiState.value.password)
        if (validationResult.isValid) {
            viewModelScope.launch {
                val model = LoginUiModel(uiState.value.email, uiState.value.password).toLoginUserRequest()
                loginRequestUseCase(model).onSuccess { userResponse ->
                    if (uiState.value.rememberMeIsChecked) saveRememberMeAndUser(userResponse)
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
    fun validateLoginInputs(email: String, password: String): ValidationResult {
        val errors = mutableListOf<String>()
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        if (email.isBlank() || !email.matches(emailRegex)) errors.add("The email is not valid.")
        if (password.isBlank()) errors.add("The password cannot be empty.")

        return ValidationResult(errors.isEmpty(), errors)
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
    private suspend fun saveRememberMeAndUser(userResponse: User) {
        dataStoreUseCases.saveRememberMeAndUserUseCase(
            rememberMe = true,
            user = userResponse.copy()
        )
    }
}
