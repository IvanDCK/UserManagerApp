package org.martarcas.usermanager.manager.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import assertk.assertThat
import org.martarcas.usermanager.manager.presentation.components.AuthTextField
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import org.martarcas.usermanager.manager.presentation.login.model.LoginUiState
import kotlin.test.Test
import kotlin.test.assertEquals

/*
@OptIn(ExperimentalTestApi::class)
class LoginScreenTest {

    @Test
    fun checkIfPasswordIsVisibleWhenPasswordVisibilityIconIsClicked() = runComposeUiTest {

        val isPasswordVisible = mutableStateOf(false)

        setContent {
            val loginUiState = LoginUiState(
                password = "mypassword",
                isPasswordVisible = isPasswordVisible.value
            )

            LoginContent(
                uiState = loginUiState,
                onAction = {
                    when (it) {
                        LoginActions.OnPasswordVisibleClick -> {
                            isPasswordVisible.value = !isPasswordVisible.value
                        }
                    }
                },
                modifier = Modifier
            )
        }

        // Simulate clicking the password visibility icon
        onNodeWithContentDescription("Toggle password visibility").performClick()

        // Check if the password is now visible
        assertEquals(expected = true, actual = isPasswordVisible.value)

        // Simulate clicking the password visibility icon again
        onNodeWithContentDescription("Toggle password visibility").performClick()

        // Check if the password is now hidden
        assertEquals(expected = false, actual = isPasswordVisible.value)
    }
}*/
