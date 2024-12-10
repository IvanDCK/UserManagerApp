package org.martarcas.usermanager.manager.presentation.list


import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.martarcas.usermanager.MainActivity
import org.martarcas.usermanager.app.presentation.AppViewModel
import org.martarcas.usermanager.core.domain.model.Result
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.model.user.UserPublic
import org.martarcas.usermanager.manager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.manager.presentation.list.model.UserListAction
import org.martarcas.usermanager.manager.presentation.login.LoginViewModel
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import org.martarcas.usermanager.manager.presentation.signup.SignUpViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class UserListScreenTest: KoinTest {

    private val dummyList = mutableStateOf(
        listOf(
            UserPublic(1, "David", "Doe", Role.BACKEND_DEVELOPER),
            UserPublic(2, "John", "Smith", Role.MOBILE_DEVELOPER),
            UserPublic(3, "Jane", "Doe", Role.PROJECT_MANAGER),
            UserPublic(4, "Albert", "Smith", Role.CEO),
            UserPublic(5, "Alice", "Doe", Role.NEW_USER),
        )
    )

    private val loggedUser = User(
        2, "John", "Smith", "logged@user.com", "12345678", Role.MOBILE_DEVELOPER
    )

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var appViewModel: AppViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var signUpViewModel: SignUpViewModel
    @MockK
    lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @MockK
    lateinit var updateUserUseCase: UpdateUserUseCase

    @MockK
    lateinit var readLoggedUserUseCase: ReadUserUseCase

    @MockK
    lateinit var readRememberMeUseCase: ReadRememberMeUseCase

    @MockK
    lateinit var saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase

    @MockK
    lateinit var dataStoreUseCases: DataStoreUseCases

    @MockK
    lateinit var loginRequestUseCase: LoginRequestUseCase

    private lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {

        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        readLoggedUserUseCase = mockk()
        dataStoreUseCases = mockk()
        saveRememberMeAndUserUseCase = mockk()
        getAllUsersUseCase = mockk()
        updateUserUseCase = mockk()
        readRememberMeUseCase = mockk()
        loginRequestUseCase = mockk()

        coEvery { loginRequestUseCase.invoke(any()) } returns Result.Success(loggedUser)
        coEvery { dataStoreUseCases.readUserUseCase.invoke() } returns flow { emit(loggedUser) }
        every { dataStoreUseCases.readRememberMeUseCase.invoke() } returns flow { emit(true) }
        coEvery { dataStoreUseCases.saveRememberMeAndUserUseCase.invoke(true, loggedUser) } returns Unit
        coEvery { getAllUsersUseCase.invoke() } returns Result.Success(dummyList.value)

        appViewModel = get()
        loginViewModel = get()
        signUpViewModel = get()
        userListViewModel = get()

        loginViewModel.onAction(LoginActions.OnEmailChange("logged@user.com"))
        loginViewModel.onAction(LoginActions.OnPasswordChange("12345678"))
        loginViewModel.onAction(LoginActions.OnLoginButtonClick)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun validateStateFlowUpdates() = runTest(testDispatcher) {

        userListViewModel.onAction(UserListAction.OnRoleFilterClick(Role.PROJECT_MANAGER))

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        runCurrent()

        val updatedState = userListViewModel.state.value
        println("updatedState: $updatedState, size roles: ${updatedState.selectedRoles.size}")
        assertEquals(1, updatedState.selectedRoles.size)
        assertEquals(Role.PROJECT_MANAGER, updatedState.searchResults.first().role)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resultListFiltersBySelectedRoles() = runTest(testDispatcher) {

        val stateValue = userListViewModel.state.value

        composeTestRule.runOnIdle {
            // Filter by PROJECT MANAGER role, expect 1 result and PROJECT MANAGER as the first result
            composeTestRule.onNodeWithText("PROJECT MANAGER").performClick()

            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            runCurrent()

            assertEquals(expected = 1, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 1, actual = stateValue.searchResults.size)
            assertEquals(expected = Role.PROJECT_MANAGER, actual = stateValue.searchResults.first().role)
            // Add CEO role to the filter, expect 2 results
            composeTestRule.onNodeWithText("CEO").performClick()

            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            runCurrent()

            assertEquals(expected = 2, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 2, actual = stateValue.searchResults.size)

            // Remove PROJECT MANAGER role from the filter, expect 1 result and role CEO as the first result
            composeTestRule.onNodeWithText("PROJECT MANAGER").performClick()

            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            runCurrent()

            assertEquals(expected = 1, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 1, actual = stateValue.searchResults.size)
            assertEquals(expected = Role.CEO, actual = stateValue.searchResults.first().role)

            // Remove CEO role from the filter, expect 5 results (original list)
            composeTestRule.onNodeWithText("CEO").performClick()
            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            runCurrent()

            assertEquals(expected = 0, actual = stateValue.selectedRoles.size)
            assertEquals(expected = 5, actual = stateValue.searchResults.size)
        }



    }


     @OptIn(ExperimentalCoroutinesApi::class)
     @Test
    fun sortButtonSortsByRoleImportanceAscendingThenDescending() = runTest(testDispatcher) {

         val stateValue = userListViewModel.state.value


         composeTestRule.runOnIdle {
             composeTestRule.onNodeWithContentDescription("Sort button icon").assertExists()

             advanceUntilIdle()
             testDispatcher.scheduler.advanceUntilIdle()
             runCurrent()

             assertNotEquals(stateValue.searchResults.first().role, Role.CEO)

             composeTestRule.onNodeWithContentDescription("Sort button icon").performClick()

             advanceUntilIdle()
             testDispatcher.scheduler.advanceUntilIdle()
             runCurrent()

             assertEquals(expected = Role.CEO, actual = stateValue.searchResults.first().role)

             composeTestRule.onNodeWithContentDescription("Sort button icon").performClick()

             advanceUntilIdle()
             testDispatcher.scheduler.advanceUntilIdle()
             runCurrent()

             assertEquals(expected = Role.NEW_USER, actual = stateValue.searchResults.first().role)

         }

    }

    @Test
    fun searchResultsChangesWhenItsSearchedByNameOrSurnameAndResetsWhenQueryIsEmpty() = runTest(testDispatcher) {

        val stateValue = userListViewModel.state.value

        //assertEquals(expected = 5, actual = stateValue.searchResults.size)

        userListViewModel.onAction(UserListAction.OnSearchQueryChange("Smith"))

        assertEquals(expected = 2, actual = stateValue.searchResults.size)

        userListViewModel.onAction(UserListAction.OnSearchQueryChange(""))

        assertEquals(expected = 5, actual = stateValue.searchResults.size)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loggedUserOnlyHasTheOptionToUpdateOwnInfoAndOpensBottomSheet() = runTest(testDispatcher) {

        val stateValue = userListViewModel.state.value

        assertEquals(expected = false, actual = stateValue.isUpdateBottomSheetOpen)

        composeTestRule.runOnIdle {
            composeTestRule.onNodeWithText("Delete").assertDoesNotExist()
            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()

            runCurrent()

            composeTestRule.onNodeWithText("Change role").assertDoesNotExist()
            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()

            runCurrent()
        }



    }

}
