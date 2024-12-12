package org.martarcas.usermanager.manager.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.martarcas.usermanager.core.di.datastoreModule
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.core.navigation.Login
import org.martarcas.usermanager.manager.data.remote.network.UserApiImpl
import org.martarcas.usermanager.manager.data.remote.repository.UserRepositoryImpl
import org.martarcas.usermanager.manager.di.databaseModule
import org.martarcas.usermanager.manager.domain.UserRepository
import org.martarcas.usermanager.manager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/*
@OptIn(ExperimentalTestApi::class)
class LoginViewModelTest : KoinTest {

    private val testDomainModule = module {
        single { UserApiImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
        single<LoginRequestUseCase> { LoginRequestUseCase(get()) }
        viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
    }

    private val loginViewModel: LoginViewModel by inject()

    @BeforeTest
    fun setUp() {
        stopKoin()

        startKoin {
            modules(datastoreModule, testDomainModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkIfPasswordIsVisibleWhenPasswordVisibilityIconIsClicked() = runComposeUiTest {

        setContent {
            val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

            LoginContent(
                modifier = Modifier,
                uiState = uiState,
                onAction = {
                    when (it) {
                        LoginActions.OnPasswordVisibleClick -> {
                            loginViewModel.onAction(it)
                        }
                    }
                }
            )
        }

        // Initial state should be not visible
        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)

        // Simulate clicking the password visibility icon
        onNodeWithContentDescription("Toggle password visibility").performClick()

        // Password visibility should be true
        assertEquals(expected = true, actual = loginViewModel.uiState.value.isPasswordVisible)

        // Simulate clicking the password visibility icon again
        onNodeWithContentDescription("Toggle password visibility").performClick()

        // Password visibility should be false
        assertEquals(expected = false, actual = loginViewModel.uiState.value.isPasswordVisible)
    }
}*/
