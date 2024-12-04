package org.martarcas.usermanager.manager.presentation.list.model

import org.martarcas.usermanager.manager.domain.model.Role

sealed interface UserListAction {
    data class OnSearchQueryChange(val query: String): UserListAction
    data object OnSortIconClick: UserListAction
    data class OnRoleFilterClick(val role: Role): UserListAction
    data class OnChangeRoleClick(val id: Int): UserListAction
    data class OnChangeRoleApply(val id: Int, val role: Role): UserListAction
    data class OnUpdateInfoClick(val id: Int): UserListAction
    data class OnDeleteClick(val id: Int): UserListAction
    data class OnDeleteConfirm(val id: Int): UserListAction
}