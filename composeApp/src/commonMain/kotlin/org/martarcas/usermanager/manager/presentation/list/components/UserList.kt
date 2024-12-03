package org.martarcas.usermanager.manager.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.model.user.UserPublic
import org.martarcas.usermanager.manager.presentation.list.UserListState

@Composable
fun UserList(
    loggedUser: User,
    users: List<UserPublic>,
    state: UserListState,
    onUpdateInfoClick: (Int) -> Unit,
    onChangeRoleClick: (Int) -> Unit,
    onChangeRoleApply: (Int, Role) -> Unit,
    onDeleteClick: (Int) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = users,
            key = { it.id }
        ) { user ->
            UserListItem(
                user = user,
                loggedUser = loggedUser,
                isDropdownOpen = state.isChangeRoleDropdownOpen && state.selectedUserId == user.id,
                onUpdateInfoClick = { onUpdateInfoClick(user.id) },
                onChangeRoleClick = { onChangeRoleClick(user.id) },
                onChangeRoleApply = { userId, newRole ->
                    onChangeRoleApply(userId, newRole) } ,
                onDeleteClick = { onDeleteClick(user.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }
    }
}