package org.martarcas.usermanager.manager.presentation.login

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.martarcas.domain.model.user.Role
import com.martarcas.domain.model.user.User
import com.martarcas.domain.use_cases.auth.LoginRequestUseCase
import com.martarcas.domain.use_cases.datastore.ReadRememberMeUseCase
import com.martarcas.domain.use_cases.datastore.ReadUserUseCase
import com.martarcas.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import com.martarcas.feature.presentation.login.LoginContent
import com.martarcas.feature.presentation.login.LoginViewModel
import com.martarcas.feature.presentation.login.model.LoginActions
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.martarcas.usermanager.presentation.AppViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class LoginViewModelTest {


    private val loggedUser = User(
        2, "John", "Smith", "logged@user.com", "12345678", Role.MOBILE_DEVELOPER, ""
    )

    private lateinit var appViewModel: AppViewModel
    private lateinit var loginViewModel: LoginViewModel

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var readLoggedUserUseCase: ReadUserUseCase


    @MockK
    lateinit var saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase


    @MockK
    lateinit var readRememberMeUseCase: ReadRememberMeUseCase

    @MockK
    lateinit var loginRequestUseCase: LoginRequestUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private val scope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        readLoggedUserUseCase = mockk()
        saveRememberMeAndUserUseCase = mockk()
        readRememberMeUseCase = mockk()
        loginRequestUseCase = mockk()

        //coEvery { loginRequestUseCase.invoke(any()) } returns Result.Success(loggedUser)
        //coEvery { dataStoreUseCases.readUserUseCase.invoke() } returns flow { emit(loggedUser) }
        every { readRememberMeUseCase.invoke() } returns flow { emit(false) }
        //coEvery { dataStoreUseCases.saveRememberMeAndUserUseCase.invoke(false, loggedUser) } returns Unit

        appViewModel = AppViewModel(
            readRememberMeUseCase = readRememberMeUseCase
        )

        loginViewModel = LoginViewModel(
            loginRequestUseCase = loginRequestUseCase,
            saveRememberMeAndUserUseCase = saveRememberMeAndUserUseCase,
        )

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

   @OptIn(ExperimentalCoroutinesApi::class)
   @Test
    fun checkIfPasswordIsVisibleWhenPasswordVisibilityIconIsClicked() = scope.runTest {

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.waitForIdle()

        composeTestRule.mainClock.advanceTimeBy(2000)

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
        advanceUntilIdle()


        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
        println("Starting value " + loginViewModel.uiState.value.isPasswordVisible)


        composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()

        composeTestRule.waitForIdle()
        advanceUntilIdle()

        println("First click -> " + loginViewModel.uiState.value.isPasswordVisible)
        assertEquals(expected = true, actual = loginViewModel.uiState.value.isPasswordVisible)


         composeTestRule.onNodeWithContentDescription("Toggle password visibility").performClick()

        composeTestRule.waitForIdle()
        advanceUntilIdle()

        println("Second click -> " + loginViewModel.uiState.value.isPasswordVisible)
        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
    }

    @Test
    fun checkIfPasswordIsVisibleWhenPasswordVisibilityIconIsClickedV2() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(2000)

        composeTestRule.waitForIdle()

        composeTestRule.setContent {
            LoginContent(
                modifier = Modifier,
                onAction = { loginViewModel.onAction(it) },
                uiState = loginViewModel.uiState.value
            )
        }

        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
        loginViewModel.onAction(LoginActions.OnPasswordVisibleClick)

        composeTestRule.waitForIdle()

        assertEquals(expected = true, actual = loginViewModel.uiState.value.isPasswordVisible)
        loginViewModel.onAction(LoginActions.OnPasswordVisibleClick)

        composeTestRule.waitForIdle()

        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
    }
}