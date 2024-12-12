package org.martarcas.usermanager.manager.presentation.list
/*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.martarcas.usermanager.core.domain.DataStoreRepository
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.manager.core.FakeDataStoreRepositoryImpl
import org.martarcas.usermanager.manager.data.remote.network.UserApi
import org.martarcas.usermanager.manager.data.remote.network.UserApiImpl
import org.martarcas.usermanager.manager.data.remote.repository.UserRepositoryImpl
import org.martarcas.usermanager.manager.di.databaseModule
import org.martarcas.usermanager.manager.di.platformModule
import org.martarcas.usermanager.manager.domain.UserRepository
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.model.user.UserPublic
import org.martarcas.usermanager.manager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.manager.presentation.list.model.UserListAction
import org.martarcas.usermanager.manager.presentation.list.model.UserListState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/*@OptIn(ExperimentalTestApi::class)
class UserListScreenTest: KoinTest {

    private lateinit var userListViewModel: UserListViewModel

    private lateinit var koinApp: KoinApplication

    private val testModule = module {
        singleOf(::UserRepositoryImpl).bind<UserRepository>()
        singleOf(::UserApiImpl).bind<UserApi>()

        singleOf(::GetAllUsersUseCase)
        singleOf(::UpdateUserUseCase)
        singleOf(::UpdateRoleUseCase)
        singleOf(::DeleteUserUseCase)

        singleOf(::FakeDataStoreRepositoryImpl).bind<DataStoreRepository>()
        singleOf(::SaveRememberMeAndUserUseCase)
        singleOf(::ReadRememberMeUseCase)
        singleOf(::ReadUserUseCase)

        singleOf(::DataStoreUseCases)

        viewModel<UserListViewModel> {
            UserListViewModel(get(), get(), get(), get(), get())
        }

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        // Start Koin
        stopKoin()

         koinApp = koinApplication {
            startKoin {
                modules(
                    testModule, platformModule, databaseModule
                )
            }
        }

        // Set the main dispatcher for testing
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        // Stop Koin
        stopKoin()
        //koinApp.close()
        Dispatchers.resetMain()
    }

    private val dummyList = mutableStateOf(
        listOf(
            UserPublic(1, "David", "Doe", Role.BACKEND_DEVELOPER),
            UserPublic(2, "John", "Smith", Role.MOBILE_DEVELOPER),
            UserPublic(3, "Jane", "Doe", Role.PROJECT_MANAGER),
            UserPublic(4, "Albert", "Smith", Role.CEO),
            UserPublic(5, "Alice", "Doe", Role.NEW_USER),
        )
    )

    val coroutineScope = CoroutineScope(Dispatchers.Unconfined)


    private val loggedUser = User(
        2, "John", "Smith", "logged@user.com", "12345678", Role.MOBILE_DEVELOPER
    )

    private val sortAscending = mutableStateOf(false)

    private val searchQuery = mutableStateOf("")

    private val state = mutableStateOf(
        UserListState(
            searchResults = dummyList.value,
            searchQuery = searchQuery.value,
            loggedUser = loggedUser,
            sortAscending = sortAscending.value
        )
    )



    @Test
    fun resultListFiltersBySelectedRoles() = runComposeUiTest {

        setContent {
            userListViewModel = koinViewModel<UserListViewModel>()

           val state2 by userListViewModel.state.collectAsState()

            coroutineScope.launch {
                userListViewModel.updateState {
                    copy(searchResults = dummyList.value)
                }
                delay(10000)
            }

            UserListScreen(
                loggedUser = loggedUser,
                sortAscending = state2.sortAscending,
                state = state2,
                onAction = { action ->
                    when (action) {
                        is UserListAction.OnRoleFilterClick -> {
                            userListViewModel.onAction(action)
                        }
                        else -> {}
                    }
                },
                onBottomSheetAction = {}
            )
        }


        runOnIdle {

            val stateValue = userListViewModel.state.value

            // Filter by PROJECT MANAGER role, expect 1 result and PROJECT MANAGER as the first result
            onNodeWithText("PROJECT MANAGER").performClick()
            assertEquals(expected = 1, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 1, actual = stateValue.searchResults.size)
            assertEquals(expected = Role.PROJECT_MANAGER, actual = stateValue.searchResults.first().role)
            // Add CEO role to the filter, expect 2 results
            onNodeWithText("CEO").performClick()
            assertEquals(expected = 2, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 2, actual = stateValue.searchResults.size)

            // Remove PROJECT MANAGER role from the filter, expect 1 result and role CEO as the first result
            onNodeWithText("PROJECT MANAGER").performClick()
            assertEquals(expected = 1, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 1, actual = stateValue.searchResults.size)
            assertEquals(expected = Role.CEO, actual = stateValue.searchResults.first().role)

            // Remove CEO role from the filter, expect 5 results (original list)
            onNodeWithText("CEO").performClick()
            assertEquals(expected = 0, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 5, actual = stateValue.searchResults.size)
        }
    }

    *//*
     @Test
    fun sortButtonSortsByRoleImportanceAscendingThenDescending() = runComposeUiTest {

        setContent {
            UserListScreen(
                loggedUser = loggedUser,
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

    @Test
    fun searchResultsChangesWhenItsSearchedByNameOrSurnameAndResetsWhenQueryIsEmpty() = runComposeUiTest {

        setContent {
            UserListScreen(
                loggedUser = loggedUser,
                sortAscending = sortAscending.value,
                state = state.value,
                onAction = { action ->
                    when (action) {
                        is UserListAction.OnSearchQueryChange -> {
                            searchQuery.value = action.query
                            val sortedList = if (action.query.isEmpty()) {
                                dummyList.value
                            } else {
                                dummyList.value.filter { it.name.contains(action.query, ignoreCase = true) || it.surname.contains(action.query, ignoreCase = true) }
                            }
                            dummyList.value = sortedList
                            state.value = state.value.copy(searchResults = dummyList.value, searchQuery = searchQuery.value)
                        }
                        else -> {}
                    }
                },
                onBottomSheetAction = {}
            )
        }

        val originalList = dummyList.value

        assertEquals(expected = 5, actual = state.value.searchResults.size)

        searchQuery.value = "Smith"

        val sortedList = if (searchQuery.value.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.name.contains(searchQuery.value, ignoreCase = true) ||
                    it.surname.contains(searchQuery.value, ignoreCase = true)
            }
        }

        dummyList.value = sortedList
        state.value = state.value.copy(searchResults = dummyList.value, searchQuery = searchQuery.value)

        assertEquals(expected = 2, actual = state.value.searchResults.size)

        searchQuery.value = ""
        val sortedList2 = if (searchQuery.value.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.name.contains(searchQuery.value, ignoreCase = true) ||
                        it.surname.contains(searchQuery.value, ignoreCase = true)
            }
        }
        dummyList.value = sortedList2

        state.value = state.value.copy(searchResults = dummyList.value, searchQuery = searchQuery.value)

        assertEquals(expected = 5, actual = state.value.searchResults.size)

    }

    @Test
    fun loggedUserOnlyHasTheOptionToUpdateOwnInfoAndOpensBottomSheet() =  runComposeUiTest {

        setContent {
            UserListScreen(
                loggedUser = loggedUser,
                sortAscending = sortAscending.value,
                state = state.value,
                onAction = { action ->
                    when (action) {
                        is UserListAction.OnUpdateInfoClick -> {
                            state.value = state.value.copy(
                                isUpdateBottomSheetOpen = true,
                                selectedUserId = action.id
                            )
                        }

                        else -> {}
                    }
                },
                onBottomSheetAction = { action ->
                    when (action) {
                        is UpdateInfoBottomSheetActions.OnUpdateInfoClick -> {
                            state.value = state.value.copy(
                                isUpdateBottomSheetOpen = false,
                                selectedUserId = 0
                            )
                        }
                        else -> {}
                    }

                }
            )
            UserListItem(
                loggedUser = loggedUser,
                user = UserPublic(2, "John", "Smith", Role.MOBILE_DEVELOPER),
                isDropdownOpen = state.value.isChangeRoleDropdownOpen,
                onUpdateInfoClick = {
                    state.value = state.value.copy(
                        isUpdateBottomSheetOpen = true,
                        selectedUserId = 2
                    )
                },
                onChangeRoleClick = {  },
                onChangeRoleApply = { _, _ ->  },
                onDeleteClick = {  }
            )
        }

        assertEquals(expected = false, actual = state.value.isUpdateBottomSheetOpen)

        onNodeWithText("Delete").assertDoesNotExist()

        onNodeWithText("Change role").assertDoesNotExist()


    }
     *//*


}*/

 */
