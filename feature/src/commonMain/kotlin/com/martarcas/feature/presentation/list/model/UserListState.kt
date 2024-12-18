package com.martarcas.feature.presentation.list.model

import com.martarcas.domain.model.user.Role
import com.martarcas.domain.model.user.User
import com.martarcas.domain.model.user.UserPublic
import com.martarcas.feature.presentation.ui_utils.UiText

data class UserListState (
    val searchQuery : String = "",
    val searchResults : List<UserPublic> = emptyList(),
    val sortAscending : Boolean = true,
    val isChangeRoleDropdownOpen: Boolean = false,
    val selectedUserId: Int? = null,
    val selectedRoles: List<Role> = emptyList(),
    val isDeleteDialogOpen: Boolean = false,
    val isLoading : Boolean = true,
    val errorMessage: UiText? = null,
    val isUpdateBottomSheetOpen: Boolean = false,
    val loggedUser: User? = null,
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val avatarId: String = "",
    val isPasswordVisible: Boolean = false,
    val isUpdateInfoLoading: Boolean = false,
    val bottomSheetErrorMessage: UiText? = null
)