package org.martarcas.usermanager.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.martarcas.usermanager.data.remote.requests.DeleteUserRequest
import org.martarcas.usermanager.data.remote.requests.UpdateUserRequest
import org.martarcas.usermanager.domain.model.response.onError
import org.martarcas.usermanager.domain.model.response.onSuccess
import org.martarcas.usermanager.domain.model.user.Role
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.presentation.profile.model.AvatarBottomSheetActions
import org.martarcas.usermanager.presentation.profile.model.DeleteDialogActions
import org.martarcas.usermanager.presentation.profile.model.ProfileActions
import org.martarcas.usermanager.presentation.profile.model.ProfileUiState
import org.martarcas.usermanager.presentation.ui_utils.UiText
import org.martarcas.usermanager.presentation.ui_utils.toUiText
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.user_avatar0
import usermanagerapp.composeapp.generated.resources.user_avatar1
import usermanagerapp.composeapp.generated.resources.user_avatar2
import usermanagerapp.composeapp.generated.resources.user_avatar3
import usermanagerapp.composeapp.generated.resources.user_avatar4
import usermanagerapp.composeapp.generated.resources.user_avatar5
import usermanagerapp.composeapp.generated.resources.user_avatar6
import usermanagerapp.composeapp.generated.resources.user_avatar7

@KoinViewModel
class ProfileViewModel(
    @Provided private val saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase,
    @Provided private val readUserUseCase: ReadUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState
        .onStart {
            getLoggedUser()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    private fun getLoggedUser() {
        viewModelScope.launch {
            readUserUseCase().collect { user ->
                updateProfileState {
                    updateFromUser(user)
                }
            }
        }
    }

    private fun updateProfileState(update: ProfileUiState.() -> ProfileUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun onProfileAction(action: ProfileActions) {
        when (action) {
            is ProfileActions.OnLogoutButtonClick -> {
                updateProfileState { copy(loggedUser = null) }
                viewModelScope.launch {
                    saveRememberMeAndUserUseCase.invoke(
                        false,
                        User(0, "", "", "", "", Role.NEW_USER, avatarId = "")
                    )
                }
            }

            is ProfileActions.OnEditAvatarButtonClick -> updateProfileState {
                copy(
                    showAvatarChooserDialog = true
                )
            }

            is ProfileActions.OnFirstNameChange -> updateProfileState { copy(firstNameText = action.value) }
            is ProfileActions.OnLastNameChange -> updateProfileState { copy(lastNameText = action.value) }
            is ProfileActions.OnEmailChange -> updateProfileState { copy(emailText = action.value) }
            is ProfileActions.OnPasswordChange -> updateProfileState { copy(passwordText = action.value) }
            is ProfileActions.OnPasswordVisibleClick -> updateProfileState { copy(isPasswordVisible = !isPasswordVisible) }
            is ProfileActions.OnSaveButtonClick -> {
                updateUser()
            }

            is ProfileActions.OnRemoveAccountButtonClick -> updateProfileState {
                copy(
                    showRemoveAccountPopup = true
                )
            }
        }
    }

    fun onDeleteDialogAction(action: DeleteDialogActions) {
        when (action) {
            is DeleteDialogActions.OnConfirmClick -> handleDeleteUser()
            is DeleteDialogActions.OnDismissClick -> updateProfileState {
                copy(
                    showRemoveAccountPopup = false
                )
            }
        }
    }

    fun onAvatarBottomSheetAction(action: AvatarBottomSheetActions) {
        when (action) {
            is AvatarBottomSheetActions.OnSelectNewAvatar -> {
                updateProfileState {
                    copy(
                        showAvatarChooserDialog = false,
                        avatarId = action.avatarId
                    )
                }
            }

            is AvatarBottomSheetActions.OnDismissClick -> updateProfileState {
                copy(
                    showAvatarChooserDialog = false
                )
            }
        }
    }

    /**
     * Validates the inputs and updates the user info, then updates the datastore preferences and fetches all users
     */
    private fun updateUser() {
        viewModelScope.launch {
            updateProfileState {
                copy(
                    isLoading = true
                )
            }
            val validationResult = validateUpdateInfoInputs(
                name = _uiState.value.firstNameText,
                surname = _uiState.value.lastNameText,
                email = _uiState.value.emailText,
                password = _uiState.value.passwordText,
            )

            if (validationResult.isValid.not()) {
                updateProfileState {
                    copy(
                        errorMessage = UiText.DynamicString(
                            validationResult.errors.joinToString(
                                "\n"
                            )
                        ),
                        isLoading = false
                    )
                }
                return@launch
            }
            updateUserUseCase(
                UpdateUserRequest(
                    userId = _uiState.value.loggedUser?.id!!,
                    newName = _uiState.value.firstNameText,
                    newSurname = _uiState.value.lastNameText,
                    newEmail = _uiState.value.emailText,
                    newPassword = _uiState.value.passwordText,
                    newAvatar = _uiState.value.avatarId
                )
            )
                .onSuccess {
                    val newUserInfo = User(
                        id = _uiState.value.loggedUser?.id!!,
                        name = _uiState.value.firstNameText,
                        surname = _uiState.value.lastNameText,
                        email = _uiState.value.emailText,
                        password = _uiState.value.passwordText,
                        role = _uiState.value.loggedUser!!.role,
                        avatarId = _uiState.value.avatarId
                    )
                    updateProfileState {
                        copy(
                            isLoading = false,
                            loggedUser = newUserInfo,
                        )
                    }
                    updateSavedUser(newUserInfo)

                }
                .onError { error ->
                    updateProfileState {
                        copy(
                            errorMessage = error.toUiText(),
                            isLoading = false
                        )
                    }
                }
        }
    }

    /**
     * Updates the user in the datastore
     * @param updatedUser
     */
    private fun updateSavedUser(updatedUser: User) {
        viewModelScope.launch {
            saveRememberMeAndUserUseCase.invoke(
                rememberMe = true,
                user = updatedUser
            )
        }
    }

    private fun handleDeleteUser() {
        viewModelScope.launch {
            updateProfileState {
                copy(
                    isLoading = true
                )
            }
            deleteUserUseCase(DeleteUserRequest(_uiState.value.loggedUser?.id!!))
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showRemoveAccountPopup = false,
                            loggedUser = null
                        )
                    }
                }
                .onError { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toUiText(),
                            showRemoveAccountPopup = false,
                        )
                    }
                }
        }
    }

    fun avatarNameDispenser(index: Int): DrawableResource {
        return when (index) {
            0 -> Res.drawable.user_avatar0
            1 -> Res.drawable.user_avatar1
            2 -> Res.drawable.user_avatar2
            3 -> Res.drawable.user_avatar3
            4 -> Res.drawable.user_avatar4
            5 -> Res.drawable.user_avatar5
            6 -> Res.drawable.user_avatar6
            7 -> Res.drawable.user_avatar7
            else -> Res.drawable.user_avatar0
        }
    }

    private fun validateUpdateInfoInputs(
        name: String,
        surname: String,
        email: String,
        password: String
    ): org.martarcas.usermanager.presentation.signup.model.ValidationResult {
        val errors = mutableListOf<String>()

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (email.isBlank() || !email.matches(emailRegex)) {
            errors.add("The email is not valid.")
        }
        if (password.isBlank()) errors.add("The password cannot be empty.")

        if (name.isBlank()) errors.add("The name cannot be empty.")
        if (surname.isBlank()) errors.add("The surname cannot be empty.")

        return org.martarcas.usermanager.presentation.signup.model.ValidationResult(
            errors.isEmpty(),
            errors
        )
    }
}
