package org.martarcas.usermanager.manager.presentation.signup

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
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
import org.martarcas.usermanager.app.presentation.AppViewModel
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.auth.SignUpRequestUseCase
import org.martarcas.usermanager.manager.presentation.signup.model.SignupActions
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SignUpViewModelTest {

    private lateinit var appViewModel: AppViewModel
    private lateinit var signUpViewModel: SignUpViewModel

    @MockK
    lateinit var signUpRequestUseCase: SignUpRequestUseCase

    @MockK
    lateinit var readLoggedUserUseCase: ReadUserUseCase

    @MockK
    lateinit var readRememberMeUseCase: ReadRememberMeUseCase

    @MockK
    lateinit var saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase

    @MockK
    lateinit var dataStoreUseCases: DataStoreUseCases

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    private val scope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        signUpRequestUseCase = mockk()
        readLoggedUserUseCase = mockk()
        saveRememberMeAndUserUseCase = mockk()
        readRememberMeUseCase = mockk()
        dataStoreUseCases = mockk()

        every { dataStoreUseCases.readRememberMeUseCase.invoke() } returns flow { emit(false) }

        appViewModel = AppViewModel(
            dataStoreUseCases = dataStoreUseCases
        )
        signUpViewModel = SignUpViewModel(
            signUpRequestUseCase = signUpRequestUseCase,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun checkIfFirstNameOnChangeIsValidUnitTest() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false

        println("0 AQUI LLEGA UNIT")
        composeTestRule.setContent {
            SignUpContent(
                modifier = Modifier,
                viewModel = signUpViewModel,
                uiState = signUpViewModel.uiState.value
            )
        }

        println("1 AQUI LLEGA UNIT")
        composeTestRule.waitForIdle()

        println("2 AQUI LLEGA UNIT")
        assertEquals("", signUpViewModel.uiState.value.firstName)

        println("3 AQUI LLEGA UNIT")
        signUpViewModel.onAction(SignupActions.OnFirstNameChange("Alejan"))
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.waitForIdle()
        assertEquals("Alejan", signUpViewModel.uiState.value.firstName)

        signUpViewModel.onAction(SignupActions.OnFirstNameChange("Alejandro"))
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.waitForIdle()
        assertEquals("Alejandro", signUpViewModel.uiState.value.firstName)
    }

    @Test
    fun checkIfFirstNameOnChangeIsValidUiTest() = scope.runTest {
        composeTestRule.mainClock.autoAdvance = false

        println("0 AQUI LLEGA UI")
        composeTestRule.setContent {
            SignUpContent(
                modifier = Modifier,
                viewModel = signUpViewModel,
                uiState = signUpViewModel.uiState.value
            )
        }

        println("1 AQUI LLEGA UI")
        composeTestRule.waitForIdle()

        println("2 AQUI LLEGA UI")
        assertEquals("", signUpViewModel.uiState.value.firstName)

        println("3 AQUI LLEGA UI")
        composeTestRule.onNodeWithTag("signup_text_field").performTextInput("Alejan")
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.waitForIdle()
        assertEquals("Alejan", signUpViewModel.uiState.value.firstName)

        composeTestRule.onNodeWithTag("signup_text_field").performTextInput("Alejandro")
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.waitForIdle()
        assertEquals("Alejandro", signUpViewModel.uiState.value.firstName)
    }
}