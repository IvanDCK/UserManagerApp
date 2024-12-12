package org.martarcas.usermanager.manager.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.get
import org.martarcas.usermanager.app.presentation.AppViewModel
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class LoginViewModelTest : KoinTest {

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

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private val scope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        readLoggedUserUseCase = mockk()
        dataStoreUseCases = mockk()
        saveRememberMeAndUserUseCase = mockk()
        readRememberMeUseCase = mockk()
        loginRequestUseCase = mockk()

        //coEvery { loginRequestUseCase.invoke(any()) } returns Result.Success(loggedUser)
        //coEvery { dataStoreUseCases.readUserUseCase.invoke() } returns flow { emit(loggedUser) }
        every { dataStoreUseCases.readRememberMeUseCase.invoke() } returns flow { emit(false) }
        //coEvery { dataStoreUseCases.saveRememberMeAndUserUseCase.invoke(false, loggedUser) } returns Unit

        appViewModel = get()
        loginViewModel = get()

        //loginViewModel.onAction(LoginActions.OnEmailChange("logged@user.com"))
        //loginViewModel.onAction(LoginActions.OnPasswordChange("12345678"))
        //loginViewModel.onAction(LoginActions.OnLoginButtonClick)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun checkEmailIsChangedOnUserInput() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            LoginContent(
                modifier = Modifier,
                onAction = { loginViewModel.onAction(it) },
                uiState = loginViewModel.uiState.value
            )
        }

        composeTestRule.waitForIdle()

        assertEquals(expected = "", actual = loginViewModel.uiState.value.email)

        loginViewModel.onAction(LoginActions.OnEmailChange("alejandro"))
        composeTestRule.waitForIdle()
        assertEquals(expected = "alejandro", actual = loginViewModel.uiState.value.email)

        loginViewModel.onAction(LoginActions.OnEmailChange("aleja"))
        composeTestRule.waitForIdle()
        assertEquals(expected = "aleja", actual = loginViewModel.uiState.value.email)

        loginViewModel.onAction(LoginActions.OnEmailChange("alejandro@gmail.com"))
        composeTestRule.waitForIdle()
        assertEquals(expected = "alejandro@gmail.com", actual = loginViewModel.uiState.value.email)
    }

    @Test
    fun checkPasswordIsChangedOnUserInput() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            LoginContent(
                modifier = Modifier,
                onAction = { loginViewModel.onAction(it) },
                uiState = loginViewModel.uiState.value)
        }

        composeTestRule.waitForIdle()

        assertEquals(expected = "", actual = loginViewModel.uiState.value.password)

        loginViewModel.onAction(LoginActions.OnPasswordChange("1234567"))
        composeTestRule.waitForIdle()
        assertEquals(expected = "1234567", actual = loginViewModel.uiState.value.password)

        loginViewModel.onAction(LoginActions.OnPasswordChange("12345678"))
        composeTestRule.waitForIdle()
        assertEquals(expected = "12345678", actual = loginViewModel.uiState.value.password)
    }

    /*   @Test
        fun checkIfPasswordIsVisibleWhenPasswordVisibilityIconIsClicked() = scope.runTest {

            composeTestRule.mainClock.autoAdvance = false

            composeTestRule.waitForIdle()

            composeTestRule.setContent {
                LoginContent(
                    modifier = Modifier,
                    onAction = {
                        loginViewModel.onAction(it)
                    },
                    uiState = loginViewModel.uiState.value
                )
            }
            composeTestRule.waitForIdle()

            assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
            println("Starting value " + loginViewModel.uiState.value.isPasswordVisible)


            composeTestRule.onNodeWithContentDescription(
                "Toggle password visibility",
                useUnmergedTree = true
            ).performClick()

            composeTestRule.waitForIdle()

            println("First click -> " + loginViewModel.uiState.value.isPasswordVisible)
            assertEquals(expected = true, actual = loginViewModel.uiState.value.isPasswordVisible)


            composeTestRule.onNodeWithContentDescription(
                "Toggle password visibility",
                useUnmergedTree = true
            ).performClick()

            composeTestRule.waitForIdle()

            println("Second click -> " + loginViewModel.uiState.value.isPasswordVisible)
            assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
        }*/

    @Test
    fun checkIfPasswordIsVisibleWhenPasswordVisibilityIconIsClickedV2() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            LoginContent(
                modifier = Modifier,
                onAction = { loginViewModel.onAction(it) },
                uiState = loginViewModel.uiState.value
            )
        }

        composeTestRule.waitForIdle()

        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
        println("Starting value -> ${loginViewModel.uiState.value.isPasswordVisible}")
        loginViewModel.onAction(LoginActions.OnPasswordVisibleClick)

        composeTestRule.waitForIdle()

        assertEquals(expected = true, actual = loginViewModel.uiState.value.isPasswordVisible)
        println("First click -> ${loginViewModel.uiState.value.isPasswordVisible}")
        loginViewModel.onAction(LoginActions.OnPasswordVisibleClick)

        composeTestRule.waitForIdle()

        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
        println("Second click -> ${loginViewModel.uiState.value.isPasswordVisible}")
    }
}
