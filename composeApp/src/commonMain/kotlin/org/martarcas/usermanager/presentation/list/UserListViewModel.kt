package org.martarcas.usermanager.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
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
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.martarcas.usermanager.data.remote.requests.DeleteUserRequest
import org.martarcas.usermanager.data.remote.requests.UpdateRoleRequest
import org.martarcas.usermanager.domain.model.activity.ActivityLog
import org.martarcas.usermanager.domain.model.response.onError
import org.martarcas.usermanager.domain.model.response.onSuccess
import org.martarcas.usermanager.domain.model.user.Role
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.model.user.UserPublic
import org.martarcas.usermanager.domain.use_cases.activity.CreateActivityLogUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.presentation.list.model.UpdateInfoBottomSheetActions
import org.martarcas.usermanager.presentation.list.model.UserListAction
import org.martarcas.usermanager.presentation.list.model.UserListState
import org.martarcas.usermanager.presentation.ui_utils.UiText
import org.martarcas.usermanager.presentation.ui_utils.toUiText

@KoinViewModel
class UserListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val changeRoleUseCase: UpdateRoleUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val createActivityLogUseCase: CreateActivityLogUseCase,
    @Provided private val saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase,
    @Provided private val readUserUseCase: ReadUserUseCase,
    private val isTestEnvironment: Boolean = false
) : ViewModel() {

    private var cachedUsers = emptyList<UserPublic>()

    private val _state = MutableStateFlow(UserListState())
    val state = _state
        .onStart {
            observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    /**
     * Observes the search query and filters the users by name and/or surname, if there are roles selected, it also filters by those roles
     * If the query is empty, it shows all users
     */
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

        val listToSearch = if (_state.value.selectedRoles.isEmpty()) {
            cachedUsers
        } else {
            filteredUsersByRole
        }

        val filteredUsers = listToSearch.filter {
            it.name.contains(query, ignoreCase = true) || it.surname.contains(
                query,
                ignoreCase = true
            )
        }

        if (filteredUsers.isEmpty()) {
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

        if (filteredUsers.isEmpty()) {
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

        if (_state.value.selectedRoles.isEmpty()) {
            _state.update {
                it.copy(
                    searchResults = cachedUsers,
                    errorMessage = null
                )
            }
        }
    }

    /**
     * Handles all the user list actions
     * @param action
     */
    fun onAction(action: UserListAction) {
        when (action) {
            is UserListAction.OnUpdateInfoClick -> {
                _state.update {
                    it.copy(
                        isUpdateBottomSheetOpen = !it.isUpdateBottomSheetOpen,
                        selectedUserId = if (it.isUpdateBottomSheetOpen) null else action.id,
                        name = _state.value.loggedUser?.name ?: "",
                        surname = _state.value.loggedUser?.surname ?: "",
                        email = _state.value.loggedUser?.email ?: "",
                        password = _state.value.loggedUser?.password ?: "",
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

            UserListAction.OnLogoutClick -> {
                _state.update {
                    it.copy(
                        loggedUser = null
                    )
                }
                viewModelScope.launch {
                    saveRememberMeAndUserUseCase.invoke(
                        false,
                        User(0, "", "", "", "", Role.NEW_USER, avatarId = "user_avatar0")
                    )
                }
            }
        }
    }

    /**
     * Handles the bottom sheet actions that opens when the user clicks on the update info button
     * @param action
     */
    fun onBottomSheetAction(action: UpdateInfoBottomSheetActions) {
        when (action) {
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
        }

    }

    /**
     * Fetches all users from the server
     */
    private fun getAllUsers() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            getAllUsersUseCase.invoke()
                .onSuccess { users ->
                    delay(1000)
                    cachedUsers = users
                    _state.update {
                        it.copy(
                            searchResults = users,
                            errorMessage = null,
                            isLoading = false
                        )
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

    /**
     * Deletes the specified user
     * @param id
     */
    private fun deleteUser(id: Int) = viewModelScope.launch {
        val fullName = getUserNameSurnameByUserId(_state.value.loggedUser!!.id)
        val fullTargetName = getUserNameSurnameByUserId(id)

        println("NAME AND TARGET NAME: $fullName $fullTargetName")
        deleteUserUseCase.invoke(DeleteUserRequest(id))
            .onSuccess {
                _state.update {
                    it.copy(
                        isDeleteDialogOpen = false,
                        selectedUserId = null
                    )
                }
                getAllUsers()
                createActivityLog(
                    ActivityLog(
                        fullName,
                        "DeleteUser",
                        "none",
                        fullTargetName,
                        getCurrentTimestamp()
                    )
                )
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

    /**
     * Changes the role of the specified user
     * @param id
     * @param role
     */
    private fun changeRole(id: Int, role: Role) = viewModelScope.launch {
        val fullName = getUserNameSurnameByUserId(_state.value.loggedUser!!.id)
        val fullTargetName = getUserNameSurnameByUserId(id)
        changeRoleUseCase.invoke(UpdateRoleRequest(id, role.name))
            .onSuccess {
                getAllUsers()
                createActivityLog(
                    ActivityLog(
                        fullName,
                        "ChangeRole",
                        role.name,
                        fullTargetName,
                        getCurrentTimestamp()
                    )
                )
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

    private fun getUserNameSurnameByUserId(userId: Int): String {
        return cachedUsers.first { it.id == userId }.name +
                " " + cachedUsers.first { it.id == userId }.surname
    }

    private fun createActivityLog(activityLog: ActivityLog) {
        viewModelScope.launch {
            createActivityLogUseCase(activityLog)
        }
    }

    private fun getCurrentTimestamp(): String {
        val currentInstant: Instant = Clock.System.now()
        return currentInstant.toEpochMilliseconds().toString()
    }

    /**
     * Retrieves the logged user from the datastore and updates the state with it
     */
    private fun getLoggedUser() {
        viewModelScope.launch {
            readUserUseCase.invoke().collect { user ->
                _state.update {
                    it.copy(
                        loggedUser = user
                    )
                }
            }
        }
    }

    init {
        getAllUsers()
        getLoggedUser()
    }
}