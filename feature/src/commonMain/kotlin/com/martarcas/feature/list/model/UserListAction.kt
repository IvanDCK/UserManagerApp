package com.martarcas.feature.list.model

import com.martarcas.domain.model.user.Role

sealed interface UserListAction {
    data class OnSearchQueryChange(val query: String): UserListAction
    data object OnSortIconClick: UserListAction
    data class OnRoleFilterClick(val role: Role): UserListAction
    data class OnChangeRoleClick(val id: Int): UserListAction
    data class OnChangeRoleApply(val id: Int, val role: Role): UserListAction
    data class OnUpdateInfoClick(val id: Int): UserListAction
    data class OnDeleteClick(val id: Int): UserListAction
    data class OnDeleteConfirm(val id: Int): UserListAction
    data object OnLogoutClick: UserListAction
}