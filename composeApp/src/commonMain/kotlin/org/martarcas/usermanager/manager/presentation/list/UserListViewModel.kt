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
import org.martarcas.usermanager.core.domain.onError
import org.martarcas.usermanager.core.domain.onSuccess
import org.martarcas.usermanager.core.presentation.UiText
import org.martarcas.usermanager.core.presentation.toUiText
import org.martarcas.usermanager.manager.data.dto.requests.DeleteUserRequest
import org.martarcas.usermanager.manager.data.dto.requests.UpdateRoleRequest
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.UserPublic
import org.martarcas.usermanager.manager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateUserUseCase


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
                            it.searchResults.sortedBy { it.role.importance }
                        } else {
                            it.searchResults.sortedByDescending { it.role.importance }
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

    init {
        getAllUsers()
    }
}