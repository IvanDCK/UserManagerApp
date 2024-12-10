package org.martarcas.usermanager.manager.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.martarcas.usermanager.MainActivity
import org.martarcas.usermanager.app.presentation.AppViewModel
import org.martarcas.usermanager.core.domain.DataStoreRepository
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
import org.martarcas.usermanager.manager.presentation.list.UserListViewModel
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import org.martarcas.usermanager.manager.presentation.signup.SignUpViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class LoginViewModelTest: KoinTest {

    private val loggedUser = User(
        2, "John", "Smith", "logged@user.com", "12345678", Role.MOBILE_DEVELOPER
    )

    private lateinit var appViewModel: AppViewModel
    private lateinit var loginViewModel: LoginViewModel

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

    /*@get:Rule
    val mockProvider = MockProvider.register { clazz ->
        mockkClass(clazz)
    }*/

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        testDispatcher = UnconfinedTestDispatcher() // Ensure synchronization
        Dispatchers.setMain(testDispatcher)

        readLoggedUserUseCase = mockk()
        dataStoreUseCases = mockk()
        saveRememberMeAndUserUseCase = mockk()
        readRememberMeUseCase = mockk()
        loginRequestUseCase = mockk()

        coEvery { loginRequestUseCase.invoke(any()) } returns Result.Success(loggedUser)
        coEvery { dataStoreUseCases.readUserUseCase.invoke() } returns flow { emit(loggedUser) }
        every { dataStoreUseCases.readRememberMeUseCase.invoke() } returns flow { emit(true) }
        coEvery { dataStoreUseCases.saveRememberMeAndUserUseCase.invoke(true, loggedUser) } returns Unit

        appViewModel = get()
        loginViewModel = get()

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

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkIfPasswordIsVisibleWhenPasswordVisibilityIconIsClicked() = runComposeUiTest {
        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.runOnIdle {
            val state = loginViewModel.uiState.value

            assertEquals(expected = false, actual = state.isPasswordVisible)
            composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()

            composeTestRule.mainClock.advanceTimeBy(500)
            assertEquals(expected = true, actual = state.isPasswordVisible)
            composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()

            composeTestRule.mainClock.advanceTimeBy(500)
            assertEquals(expected = false, actual = state.isPasswordVisible)
        }
    }
}