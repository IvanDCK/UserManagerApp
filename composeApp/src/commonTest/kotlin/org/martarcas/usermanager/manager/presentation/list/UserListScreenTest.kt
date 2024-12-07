package org.martarcas.usermanager.manager.presentation.list

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.UserPublic
import org.martarcas.usermanager.manager.presentation.list.model.UserListAction
import org.martarcas.usermanager.manager.presentation.list.model.UserListState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalTestApi::class)
class UserListScreenTest {

    private val dummyList = mutableStateOf(
        listOf(
            UserPublic(1, "David", "Doe", Role.BACKEND_DEVELOPER),
            UserPublic(2, "John", "Smith", Role.MOBILE_DEVELOPER),
            UserPublic(3, "Jane", "Doe", Role.PROJECT_MANAGER),
            UserPublic(4, "Albert", "Smith", Role.CEO),
            UserPublic(5, "Alice", "Doe", Role.NEW_USER),
        )
    )

    private val sortAscending = mutableStateOf(false)


    private val state = mutableStateOf(
        UserListState(
            searchResults = dummyList.value,
            loggedUser = null,
            sortAscending = sortAscending.value
        )
    )
    @Test
    fun resultListFiltersBySelectedRoles() = runComposeUiTest {

        setContent {
            UserListScreen(
                loggedUser = null,
                sortAscending = sortAscending.value,
                state = state.value,
                onAction = { action ->
                    when (action) {
                        is UserListAction.OnRoleFilterClick -> {

                            state.value = state.value.copy(
                                    selectedRoles = if (state.value.selectedRoles.contains(action.role)) {
                                        state.value.selectedRoles - action.role
                                    } else {
                                        state.value.selectedRoles + action.role
                                    },
                                )

                            val filteredList = if (state.value.selectedRoles.isEmpty()) {
                                dummyList.value
                            } else {
                                dummyList.value.filter { user ->
                                    state.value.selectedRoles.contains(user.role)
                                }
                            }

                            state.value = state.value.copy(selectedRoles = state.value.selectedRoles, searchResults = filteredList)

                        }
                        else -> {}
                    }
                },
                onBottomSheetAction = {}
            )
        }

        // Filter by PROJECT MANAGER role, expect 1 result and PROJECT MANAGER as the first result
        onNodeWithText("PROJECT MANAGER").performClick()
        assertEquals(expected = 1, actual = state.value.selectedRoles.size)
        assertEquals(expected = 1, actual = state.value.searchResults.size)
        assertEquals(expected = Role.PROJECT_MANAGER, actual = state.value.searchResults.first().role)
        // Add CEO role to the filter, expect 2 results
        onNodeWithText("CEO").performClick()
        assertEquals(expected = 2, actual = state.value.selectedRoles.size)
        assertEquals(expected = 2, actual = state.value.searchResults.size)

        // Remove PROJECT MANAGER role from the filter, expect 1 result and role CEO as the first result
        onNodeWithText("PROJECT MANAGER").performClick()
        assertEquals(expected = 1, actual = state.value.selectedRoles.size)
        assertEquals(expected = 1, actual = state.value.searchResults.size)
        assertEquals(expected = Role.CEO, actual = state.value.searchResults.first().role)

        // Remove CEO role from the filter, expect 5 results (original list)
        onNodeWithText("CEO").performClick()
        assertEquals(expected = 0, actual = state.value.selectedRoles.size)
        assertEquals(expected = 5, actual = state.value.searchResults.size)

    }

    @Test
    fun sortButtonSortsByRoleImportanceAscendingThenDescending() = runComposeUiTest {

        setContent {
            UserListScreen(
                loggedUser = null,
                sortAscending = sortAscending.value,
                state = state.value,
                onAction = { action ->
                    when (action) {
                        is UserListAction.OnSortIconClick -> {
                            sortAscending.value = !sortAscending.value
                            val sortedList = if (sortAscending.value) {
                                dummyList.value.sortedBy { it.role.importance }
                            } else {
                                dummyList.value.sortedByDescending { it.role.importance }
                            }
                            dummyList.value = sortedList
                            state.value = state.value.copy(searchResults = dummyList.value, sortAscending = sortAscending.value)
                        }
                        else -> {}
                    }
                },
                onBottomSheetAction = {}
            )
        }

        onNodeWithContentDescription("Sort button icon").assertExists()
        assertNotEquals(state.value.searchResults.first().role, Role.CEO)

        onNodeWithContentDescription("Sort button icon").performClick()
        assertEquals(expected = Role.CEO, actual = state.value.searchResults.first().role)

        onNodeWithContentDescription("Sort button icon").performClick()
        assertEquals(expected = Role.NEW_USER, actual = state.value.searchResults.first().role)

    }


}
