package org.martarcas.usermanager.manager.presentation.list

import org.martarcas.usermanager.core.presentation.UiText
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.UserPublic

data class UserListState (
    val searchQuery : String = "",
    val searchResults : List<UserPublic> = emptyList(),
    val sortAscending : Boolean = true,
    val isChangeRoleDropdownOpen: Boolean = false,
    val selectedUserId: Int? = null,
    val selectedRoles: List<Role> = emptyList(),
    val isDeleteDialogOpen: Boolean = false,
    val isLoading : Boolean = true,
    val errorMessage: UiText? = null
)