package com.martarcas.feature.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.martarcas.feature.presentation.components.AuthBottomBar
import com.martarcas.feature.presentation.components.AuthButton
import com.martarcas.feature.presentation.components.AuthHeader
import com.martarcas.feature.presentation.components.AuthTextField
import com.martarcas.feature.presentation.components.getPasswordVisibilityIcon
import com.martarcas.feature.presentation.login.model.LoginActions
import com.martarcas.feature.presentation.login.model.LoginUiState
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.header_signup

@Composable
fun LoginScreen(
    navigateToSignup: () -> Unit,
    navigateToList: () -> Unit
) {

    val loginViewModel: LoginViewModel = koinViewModel()

    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.shouldNavigateToSignup) {
            navigateToSignup()
            loginViewModel.onNavigatedToSignup()
        }
    }

    LaunchedEffect(uiState) {
        if (uiState.shouldNavigateToList) {
            navigateToList()
            loginViewModel.onNavigatedToList()
        }
    }

    Scaffold(
        bottomBar = {
            AuthBottomBar(infoText = "Don't have an account?", textClickable = "Register here") {
                loginViewModel.onAction(LoginActions.OnNotHaveAccountClick)
            }
        }
    ) { innerPadding ->
        LoginContent(
            modifier = Modifier.fillMaxSize().padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                top = 0.dp,
                end = innerPadding.calculateEndPadding(LayoutDirection.Rtl),
                bottom = innerPadding.calculateBottomPadding()
            ).verticalScroll(
                rememberScrollState()
            ),
            onAction = {
                loginViewModel.onAction(it)
            },
            uiState = uiState
        )
    }
}

@Composable
fun LoginContent(modifier: Modifier, onAction: (LoginActions) -> Unit, uiState: LoginUiState) {
    val textFieldModifier = Modifier
        .fillMaxWidth(0.9f)
        .height(70.dp)

    val passwordVisibilityIcon =
        getPasswordVisibilityIcon(isPasswordVisible = uiState.isPasswordVisible)


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthHeader(
            height = 300.dp,
            image = Res.drawable.header_signup,
            title = "Log in to your",
        )

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            modifier = textFieldModifier,
            value = uiState.email,
            onValueChange = {
                onAction(LoginActions.OnEmailChange(it))
            },
            label = "Email"
        )

        Spacer(modifier = Modifier.height(15.dp))

        AuthTextField(
            modifier = textFieldModifier,
            value = uiState.password,
            onValueChange = {
                onAction(LoginActions.OnPasswordChange(it))
            },
            label = "Password",
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { onAction(LoginActions.OnPasswordVisibleClick) }) {
                    Icon(
                        painter = painterResource(passwordVisibilityIcon),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        RememberMeCheckBox(uiState, onAction)

        Spacer(modifier = Modifier.height(5.dp))

        AuthButton(
            isLoading = uiState.isLoadingOnClick,
            onClick = {
                onAction(LoginActions.OnLoginButtonClick)
            },
            buttonText = "Log in"
        )

        Spacer(modifier = Modifier.height(7.dp))

        ValidationErrorBox(uiState)
    }
}

@Composable
fun RememberMeCheckBox(uiState: LoginUiState, onAction: (LoginActions) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Checkbox(
            checked = uiState.rememberMeIsChecked,
            onCheckedChange = {
                onAction(LoginActions.OnRememberMeChange)
            }
        )
        Text("Remember me")
    }
}

@Composable
fun ValidationErrorBox(uiState: LoginUiState) {
    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            uiState.validationErrors.forEach { error ->
                Text(
                    text = "* $error",
                    color = Color.Red.copy(0.7f),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Light
                )
            }
            uiState.errorMessage?.let {
                Text(
                    text = it.asString(),
                    color = Color.Red.copy(0.7f),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}
