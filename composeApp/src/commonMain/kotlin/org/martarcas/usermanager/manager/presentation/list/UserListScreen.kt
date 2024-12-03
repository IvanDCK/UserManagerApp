package org.martarcas.usermanager.manager.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.presentation.list.components.ListSearchBar
import org.martarcas.usermanager.manager.presentation.list.components.RoleButton
import org.martarcas.usermanager.manager.presentation.list.components.SortButton
import org.martarcas.usermanager.manager.presentation.list.components.UserList
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.no_search_results

@Composable
fun UserListScreenRoot(
    viewModel: UserListViewModel,
    loggedUser: User
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    UserListScreen(
        loggedUser = loggedUser,
        state = state,
        sortAscending = state.sortAscending,
        onAction = { action ->
            when(action) {
                is UserListAction.OnUpdateInfoClick -> UserListAction.OnUpdateInfoClick(action.id)
                is UserListAction.OnChangeRoleApply -> UserListAction.OnChangeRoleApply(action.id, action.role)
                is UserListAction.OnDeleteClick -> UserListAction.OnDeleteClick(action.id)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun UserListScreen(
    loggedUser: User,
    sortAscending: Boolean,
    state: UserListState,
    onAction: (UserListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchResultsListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ListSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = { query ->
                    onAction(UserListAction.OnSearchQueryChange(query))
                },
                onImeSearch = {
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth().weight(2f)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )

            SortButton(sortAscending, onAction)

        }

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            items(Role.entries.sortedBy { it.importance }){ role ->
                RoleButton(
                    text = role.name.replace("_", " ").capitalize(),
                    onClick = {
                        onAction(UserListAction.OnRoleFilterClick(role))
                    },
                    isSelected = state.selectedRoles.contains(role),
                )

                Spacer(modifier = Modifier.size(8.dp))

            }

            
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            when {
                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage.asString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                state.searchResults.isEmpty() -> {
                    Text(
                        text = stringResource(Res.string.no_search_results),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    UserList(
                        users = state.searchResults,
                        loggedUser = loggedUser,
                        state = state,
                        onDeleteClick = {
                            onAction(UserListAction.OnDeleteClick(it))
                        },
                        onChangeRoleClick = {
                            onAction(UserListAction.OnChangeRoleClick(it))
                        },
                        onChangeRoleApply = { userId, role ->
                            onAction(UserListAction.OnChangeRoleApply(userId, role))
                        },
                        onUpdateInfoClick = {
                            onAction(UserListAction.OnUpdateInfoClick(it))}
                        ,
                        modifier = Modifier.fillMaxSize(),
                        scrollState = searchResultsListState
                    )
                }
            }
        }
    }
}

