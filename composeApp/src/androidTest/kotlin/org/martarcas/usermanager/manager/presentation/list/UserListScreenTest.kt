package org.martarcas.usermanager.manager.presentation.list

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.martarcas.usermanager.domain.model.response.Result
import org.martarcas.usermanager.domain.model.user.Role
import org.martarcas.usermanager.domain.model.user.User
import org.martarcas.usermanager.domain.model.user.UserPublic
import org.martarcas.usermanager.domain.use_cases.activity.CreateActivityLogUseCase
import org.martarcas.usermanager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.presentation.app.AppViewModel
import org.martarcas.usermanager.presentation.list.UserListScreenRoot
import org.martarcas.usermanager.presentation.list.UserListViewModel
import org.martarcas.usermanager.presentation.list.model.UserListAction
import org.martarcas.usermanager.presentation.login.LoginViewModel
import org.martarcas.usermanager.presentation.login.model.LoginActions
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserListScreenTest {

    private val dummyList = mutableStateOf(
        listOf(
            UserPublic(1, "David", "Doe", Role.BACKEND_DEVELOPER, ""),
            UserPublic(2, "John", "Smith", Role.MOBILE_DEVELOPER, ""),
            UserPublic(3, "Jane", "Doe", Role.PROJECT_MANAGER, ""),
            UserPublic(4, "Albert", "Smith", Role.CEO, ""),
            UserPublic(5, "Alice", "Doe", Role.NEW_USER, ""),
        )
    )

    private val loggedUser = User(
        2, "John", "Smith", "logged@user.com", "12345678", Role.MOBILE_DEVELOPER, ""
    )

    private val loggedCEO = User(
        4, "Albert", "Smith", "ceo@user.com", "12345678", Role.CEO, ""
    )

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var appViewModel: AppViewModel
    private lateinit var loginViewModel: LoginViewModel

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @MockK
    lateinit var updateUserUseCase: UpdateUserUseCase

    @MockK
    lateinit var updateRoleUseCase: UpdateRoleUseCase

    @MockK
    lateinit var loginRequestUseCase: LoginRequestUseCase

    @MockK
    lateinit var deleteUserUseCase: DeleteUserUseCase

    @MockK
    lateinit var saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase

    @MockK
    lateinit var readUserUseCase: ReadUserUseCase

    @MockK
    lateinit var readRememberMeUseCase: ReadRememberMeUseCase
    
    @MockK
    lateinit var  createActivityLogUseCase: CreateActivityLogUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    private var testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val composeTestRule = createComposeRule()

    private val scope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getAllUsersUseCase = mockk<GetAllUsersUseCase>()
        updateUserUseCase = mockk<UpdateUserUseCase>()
        loginRequestUseCase = mockk<LoginRequestUseCase>()
        updateRoleUseCase = mockk<UpdateRoleUseCase>()
        deleteUserUseCase = mockk<DeleteUserUseCase>()
        readUserUseCase = mockk<ReadUserUseCase>()
        saveRememberMeAndUserUseCase = mockk<SaveRememberMeAndUserUseCase>()

        coEvery { loginRequestUseCase.invoke(any()) } returns Result.Success(loggedCEO)
        coEvery { readUserUseCase.invoke() } returns flow { emit(loggedCEO) }
        every { readRememberMeUseCase.invoke() } returns flow { emit(true) }
        coEvery { saveRememberMeAndUserUseCase.invoke(true, loggedCEO) } just Runs
        coEvery { getAllUsersUseCase.invoke() } returns Result.Success(dummyList.value)
        coEvery { saveRememberMeAndUserUseCase(false, any()) } just Runs

        appViewModel = AppViewModel(
            readRememberMeUseCase = readRememberMeUseCase
        )

        loginViewModel = LoginViewModel(
            loginRequestUseCase = loginRequestUseCase,
            saveRememberMeAndUserUseCase = saveRememberMeAndUserUseCase
        )

        userListViewModel = UserListViewModel(
            getAllUsersUseCase = getAllUsersUseCase,
            createActivityLogUseCase = createActivityLogUseCase,
            changeRoleUseCase = updateRoleUseCase,
            deleteUserUseCase = deleteUserUseCase,
            saveRememberMeAndUserUseCase = saveRememberMeAndUserUseCase,
            readUserUseCase = readUserUseCase,
            isTestEnvironment = true
        )

        loginViewModel.onAction(LoginActions.OnEmailChange("ceo@user.com"))
        loginViewModel.onAction(LoginActions.OnPasswordChange("12345678"))
        loginViewModel.onAction(LoginActions.OnLoginButtonClick)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
        scope.cancel()
    }

    @Test
    fun validateStateFlowUpdates() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.waitForIdle()

        composeTestRule.setContent {
            UserListScreenRoot(
                viewModel = userListViewModel
            )
        }

        userListViewModel.onAction(UserListAction.OnRoleFilterClick(Role.PROJECT_MANAGER))

        val state = userListViewModel.state.value

        assertEquals(1, state.selectedRoles.size)
        assertEquals(Role.PROJECT_MANAGER, state.selectedRoles.first())
        assertEquals(1, state.searchResults.size)
        assertEquals(Role.PROJECT_MANAGER, state.searchResults.first().role)

    }

    @Test
    fun resultListFiltersBySelectedRoles() = scope.runTest {

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.waitForIdle()

        composeTestRule.setContent {
            UserListScreenRoot(
                viewModel = userListViewModel
            )
        }

        // Filter by PROJECT MANAGER role, expect 1 result and PROJECT MANAGER as the first result
        composeTestRule.onNode(hasText("PROJECT MANAGER").and(hasClickAction())).performClick()
        composeTestRule.waitForIdle()

        assertEquals(expected = 1, actual = userListViewModel.state.value.selectedRoles.size)
        assertEquals(expected = 1, actual = userListViewModel.state.value.searchResults.size)
        assertEquals(
            expected = Role.PROJECT_MANAGER,
            actual = userListViewModel.state.value.searchResults.first().role
        )
        // Add CEO role to the filter, expect 2 results
        composeTestRule.onNode(hasText("CEO").and(hasClickAction())).performClick()
        composeTestRule.waitForIdle()

        assertEquals(expected = 2, actual = userListViewModel.state.value.selectedRoles.size)
        assertEquals(expected = 2, actual = userListViewModel.state.value.searchResults.size)

        // Remove PROJECT MANAGER role from the filter, expect 1 result and role CEO as the first result
        composeTestRule.onNode(hasText("PROJECT MANAGER").and(hasClickAction())).performClick()
        composeTestRule.waitForIdle()

        assertEquals(expected = 1, actual = userListViewModel.state.value.selectedRoles.size)
        assertEquals(expected = 1, actual = userListViewModel.state.value.searchResults.size)
        assertEquals(
            expected = Role.CEO,
            actual = userListViewModel.state.value.searchResults.first().role
        )

        // Remove CEO role from the filter, expect 5 results (original list)
        composeTestRule.onNode(hasText("CEO").and(hasClickAction())).performClick()
        composeTestRule.waitForIdle()

        assertEquals(expected = 0, actual = userListViewModel.state.value.selectedRoles.size)

        assertEquals(expected = 5, actual = userListViewModel.state.value.searchResults.size)

    }


    @Test
    fun sortButtonSortsByRoleImportanceAscendingThenDescending() = scope.runTest {

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.waitForIdle()

        composeTestRule.setContent {
            UserListScreenRoot(
                viewModel = userListViewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Sort button icon").assertExists()

        assertNotEquals(userListViewModel.state.value.searchResults.first().role, Role.CEO)

        composeTestRule.onNodeWithContentDescription("Sort button icon").performClick()

        assertEquals(
            expected = Role.CEO,
            actual = userListViewModel.state.value.searchResults.first().role
        )

        composeTestRule.onNodeWithContentDescription("Sort button icon").performClick()

        assertEquals(
            expected = Role.CEO,
            actual = userListViewModel.state.value.searchResults.first().role
        )

        composeTestRule.onNodeWithContentDescription("Sort button icon").performClick()

        assertEquals(
            expected = Role.NEW_USER,
            actual = userListViewModel.state.value.searchResults.first().role
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchResultsChangesWhenItsSearchedByNameOrSurnameAndResetsWhenQueryIsEmpty() =
        scope.runTest {

            composeTestRule.mainClock.autoAdvance = false

            composeTestRule.waitForIdle()

            composeTestRule.setContent {
                UserListScreenRoot(
                    viewModel = userListViewModel
                )
            }
            composeTestRule.waitForIdle()

            userListViewModel.onAction(UserListAction.OnSearchQueryChange("Smith"))

            advanceUntilIdle()
            composeTestRule.waitForIdle()

            assertEquals(expected = 2, actual = userListViewModel.state.value.searchResults.size)

            userListViewModel.onAction(UserListAction.OnSearchQueryChange(""))

            advanceUntilIdle()
            composeTestRule.waitForIdle()

            assertEquals(expected = 5, actual = userListViewModel.state.value.searchResults.size)

        }

    @Test
    fun loggedUserOnlyHasTheOptionToUpdateOwnInfoAndOpensBottomSheet() = scope.runTest {

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.waitForIdle()

        composeTestRule.setContent {
            UserListScreenRoot(
                viewModel = userListViewModel
            )
        }


        composeTestRule.waitForIdle()

        // Problema: No carga los nodos de la lazy column,
        // el performScrollToIndex hace scroll del lazy row de los botones de filtro de rol
        // en vez de la lazy column de la lista de usuarios,
        // por lo tanto el test no carga bien la lista y no aparecen los botones.

        composeTestRule.onNode(hasScrollAction()).performScrollToIndex(10)

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodes(isEnabled()).fetchSemanticsNodes().forEach {
            println("Node: ${it.config}")
        }
        println("USER " + userListViewModel.state.value.loggedUser)



        assertEquals(
            expected = false,
            actual = userListViewModel.state.value.isUpdateBottomSheetOpen
        )

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("John").assertExists()

        composeTestRule.onNodeWithText("Delete").assertDoesNotExist()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Change role").assertDoesNotExist()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Update info").assertExists()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Update info").performClick()

        assertEquals(
            expected = true,
            actual = userListViewModel.state.value.isUpdateBottomSheetOpen
        )
    }

    @Test
    fun checkIfLoggedUserIsDeletedOnLogoutButtonClick() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.waitForIdle()

        composeTestRule.setContent {
            UserListScreenRoot(
                viewModel = userListViewModel
            )
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("logout_button").performClick()

        assertEquals(expected = null, actual = userListViewModel.state.value.loggedUser)
    }

}






