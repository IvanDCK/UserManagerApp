package org.martarcas.usermanager.manager.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.martarcas.usermanager.core.domain.model.onError
import org.martarcas.usermanager.core.domain.model.onSuccess
import org.martarcas.usermanager.core.presentation.UiText
import org.martarcas.usermanager.core.presentation.toUiText
import org.martarcas.usermanager.manager.data.remote.requests.DeleteUserRequest
import org.martarcas.usermanager.manager.data.remote.requests.UpdateRoleRequest
import org.martarcas.usermanager.manager.data.remote.requests.UpdateUserRequest
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.UserPublic
import org.martarcas.usermanager.manager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.manager.presentation.list.model.UpdateInfoBottomSheetActions
import org.martarcas.usermanager.manager.presentation.list.model.UserListAction
import org.martarcas.usermanager.manager.presentation.list.model.UserListState
import org.martarcas.usermanager.manager.presentation.signup.model.ValidationResult


@KoinViewModel
class UserListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val changeRoleUseCase: UpdateRoleUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,

): ViewModel() {

    private var cachedUsers = emptyList<UserPublic>()

    private val _state = MutableStateFlow(UserListState())
    val state = _state
        .onStart {
            if(cachedUsers.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() && _state.value.selectedRoles.isEmpty() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedUsers
                            )
                        }
                    }

                    query.isBlank() && _state.value.selectedRoles.isNotEmpty() -> {
                        val filteredUsersByRole = cachedUsers.filter { user ->
                            _state.value.selectedRoles.contains(user.role)
                        }
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = filteredUsersByRole
                            )
                        }
                    }

                    query.length >= 2 -> {
                        filterUsersByName(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Filter users by name and/or surname
     */
    private fun filterUsersByName(query: String) {

        val filteredUsersByRole = cachedUsers.filter { user ->
            _state.value.selectedRoles.contains(user.role)
        }

        val listToSearch = if (_state.value.selectedRoles.isEmpty()){
            cachedUsers
        } else {
            filteredUsersByRole
        }

        val filteredUsers = listToSearch.filter {
            it.name.contains(query, ignoreCase = true) || it.surname.contains(query, ignoreCase = true)
        }

        if (filteredUsers.isEmpty()){
            _state.update {
                it.copy(
                    searchResults = emptyList(),
                    errorMessage = UiText.DynamicString("No users found.")
                )
            }
        } else {
            _state.update {
                it.copy(
                    searchResults = filteredUsers,
                    errorMessage = null
                )
            }
        }
    }

    /**
     * Filter users by the roles selected, if there are no roles selected, show all users
     */
    private fun filterUsersByRole() {

        val filteredUsers = cachedUsers.filter { user ->
            _state.value.selectedRoles.contains(user.role)
        }

        if (filteredUsers.isEmpty()){
            _state.update {
                it.copy(
                    searchResults = emptyList(),
                    errorMessage = UiText.DynamicString("No users found.")
                )
            }
        } else {
            _state.update {
                it.copy(
                    searchResults = filteredUsers,
                    errorMessage = null
                )
            }
        }

        if (_state.value.selectedRoles.isEmpty()){
            _state.update {
                it.copy(
                    searchResults = cachedUsers,
                    errorMessage = null
                )
            }
        }
    }

    fun onAction(action: UserListAction) {
        when(action) {
            is UserListAction.OnUpdateInfoClick -> {
                _state.update {
                    it.copy(
                        isUpdateBottomSheetOpen = !it.isUpdateBottomSheetOpen,
                        selectedUserId = if (it.isUpdateBottomSheetOpen) null else action.id,
                        name = cachedUsers.find { user ->
                            user.id == action.id
                        }?.name ?: "",
                        surname = cachedUsers.find { user ->
                            user.id == action.id
                        }?.surname ?: "",

                    )
                }
            }

            is UserListAction.OnChangeRoleApply -> {

                changeRole(action.id, action.role)

                _state.update {
                    it.copy(
                        isChangeRoleDropdownOpen = !it.isChangeRoleDropdownOpen,
                        selectedUserId = null
                    )
                }

            }
            is UserListAction.OnDeleteConfirm -> deleteUser(action.id)
            is UserListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is UserListAction.OnRoleFilterClick -> {
                _state.update {
                    it.copy(
                        selectedRoles = if (it.selectedRoles.contains(action.role)) {
                            it.selectedRoles - action.role
                        } else {
                            it.selectedRoles + action.role
                        },
                    )
                }
                filterUsersByRole()
            }
            UserListAction.OnSortIconClick -> {
                _state.update {
                    it.copy(
                        sortAscending = !it.sortAscending,
                        searchResults = if (it.sortAscending) {
                            it.searchResults.sortedBy { user ->
                                user.role.importance
                            }
                        } else {
                            it.searchResults.sortedByDescending { user ->
                                user.role.importance
                            }
                        }
                    )
                }
            }

            is UserListAction.OnChangeRoleClick -> {
                _state.update {
                    it.copy(
                        isChangeRoleDropdownOpen = !it.isChangeRoleDropdownOpen,
                        selectedUserId = action.id
                    )
                }
            }

            is UserListAction.OnDeleteClick -> {
                _state.update {
                    it.copy(
                        isDeleteDialogOpen = !it.isDeleteDialogOpen,
                        selectedUserId = action.id
                    )
                }
            }
        }
    }


    fun onBottomSheetAction(action: UpdateInfoBottomSheetActions){
        when(action) {
            is UpdateInfoBottomSheetActions.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = action.email
                    )
                }
            }
            is UpdateInfoBottomSheetActions.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = action.name
                    )
                }
            }
            is UpdateInfoBottomSheetActions.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }
            UpdateInfoBottomSheetActions.OnPasswordVisibilityChange -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }
            is UpdateInfoBottomSheetActions.OnSurnameChange -> {
                _state.update {
                    it.copy(
                        surname = action.surname
                    )
                }
            }
            UpdateInfoBottomSheetActions.OnUpdateInfoClick -> {
                updateUser()
            }
        }

    }

    private fun updateUser() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isUpdateInfoLoading = true
                )
            }

            val validationResult = validateUpdateInfoInputs(
                name = _state.value.name,
                surname = _state.value.surname,
                email = _state.value.email,
                password = _state.value.password
            )

            if (validationResult.isValid.not()) {
                _state.update {
                    it.copy(
                        bottomSheetErrorMessage = UiText.DynamicString(validationResult.errors.joinToString("\n")),
                        isUpdateInfoLoading = false
                    )
                }
                return@launch
            }

            updateUserUseCase.invoke(
                UpdateUserRequest(
                    userId = _state.value.selectedUserId!!,
                    newName = _state.value.name,
                    newSurname = _state.value.surname,
                    newEmail = _state.value.email,
                    newPassword = _state.value.password
                )
            )
                .onSuccess {
                    _state.update {
                        it.copy(
                            isUpdateInfoLoading = false,
                            bottomSheetErrorMessage = null
                        )
                    }
                    getAllUsers()
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            bottomSheetErrorMessage = error.toUiText(),
                            isUpdateInfoLoading = false
                        )
                    }
                }


        }
    }


    private fun getAllUsers() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            getAllUsersUseCase.invoke()
                .onSuccess { users ->
                    cachedUsers = users
                    _state.update {
                        it.copy(
                            searchResults = users,
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                    users.forEach {
                        println(it)
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            searchResults = emptyList(),
                            errorMessage = error.toUiText(),
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun deleteUser(id: Int) = viewModelScope.launch {
        deleteUserUseCase.invoke(DeleteUserRequest(id))
            .onSuccess {
                _state.update {
                    it.copy(
                        isDeleteDialogOpen = false,
                        selectedUserId = null
                    )
                }
                getAllUsers()
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.toUiText(),
                        isDeleteDialogOpen = false,
                        selectedUserId = null
                    )
                }
            }
    }

    private fun changeRole(id: Int, role: Role) = viewModelScope.launch {
        changeRoleUseCase.invoke(UpdateRoleRequest(id, role.name))
            .onSuccess {
                getAllUsers()
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

    private fun validateUpdateInfoInputs(
        name: String,
        surname: String,
        email: String,
        password: String
    ): ValidationResult {
        val errors = mutableListOf<String>()

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (email.isBlank() || !email.matches(emailRegex)) {
            errors.add("The email is not valid.")
        }
        if (password.isBlank()) errors.add("The password cannot be empty.")

        if (name.isBlank()) errors.add("The name cannot be empty.")
        if (surname.isBlank()) errors.add("The surname cannot be empty.")

        return ValidationResult(errors.isEmpty(), errors)
    }

    init {
        getAllUsers()
    }
}