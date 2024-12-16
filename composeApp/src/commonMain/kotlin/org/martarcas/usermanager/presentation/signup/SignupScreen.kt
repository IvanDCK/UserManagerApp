package org.martarcas.usermanager.presentation.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.presentation.components.AuthBottomBar
import org.martarcas.usermanager.presentation.components.AuthButton
import org.martarcas.usermanager.presentation.components.AuthHeader
import org.martarcas.usermanager.presentation.components.AuthTextField
import org.martarcas.usermanager.presentation.components.getPasswordVisibilityIcon
import org.martarcas.usermanager.presentation.signup.model.SignupActions
import org.martarcas.usermanager.presentation.signup.model.SignupUiState
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.header_signup

@Composable
fun SignUpScreen(navigateToLogin: () -> Unit) {
    val signupViewModel: SignUpViewModel = koinViewModel()
    val uiState by signupViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.shouldNavigateToLogin) {
            navigateToLogin()
            signupViewModel.onNavigatedToLogin()
        }
    }

    Scaffold(
        bottomBar = {
            AuthBottomBar(
                infoText = "Already have an account?",
                textClickable = "Login here"
            ) { signupViewModel.onAction(SignupActions.OnAlreadyHaveAccountClick) }
        }
    ) { innerPadding ->
        SignUpContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    top = 0.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Rtl),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState()),
            viewModel = signupViewModel,
            uiState = uiState
        )
    }
}

@Composable
fun SignUpContent(modifier: Modifier, viewModel: SignUpViewModel, uiState: SignupUiState) {
    val textFieldModifier = Modifier
        .fillMaxWidth(0.9f)
        .height(70.dp)
        .testTag("signup_text_field")

    val passwordVisibilityIcon =
        getPasswordVisibilityIcon(isPasswordVisible = uiState.isPasswordVisible)

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AuthHeader(
            height = 300.dp,
            image = Res.drawable.header_signup,
            title = "Sign in to your"
        )

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            modifier = textFieldModifier,
            value = uiState.firstName,
            onValueChange = { viewModel.onAction(SignupActions.OnFirstNameChange(it)) },
            label = "First Name"
        )
        Spacer(modifier = Modifier.height(15.dp))

        AuthTextField(
            modifier = textFieldModifier,
            value = uiState.lastName,
            onValueChange = { viewModel.onAction(SignupActions.OnLastNameChange(it)) },
            label = "Last Name"
        )
        Spacer(modifier = Modifier.height(15.dp))

        AuthTextField(
            modifier = textFieldModifier,
            value = uiState.email,
            onValueChange = { viewModel.onAction(SignupActions.OnEmailChange(it)) },
            label = "Email"
        )
        Spacer(modifier = Modifier.height(15.dp))

        AuthTextField(
            modifier = textFieldModifier,
            value = uiState.password,
            onValueChange = { viewModel.onAction(SignupActions.OnPasswordChange(it)) },
            label = "Password",
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { viewModel.onAction(SignupActions.OnPasswordVisibleClick) }) {
                    Icon(
                        painter = painterResource(passwordVisibilityIcon),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        AuthButton(
            isLoading = uiState.isLoadingOnClick,
            onClick = { viewModel.onAction(SignupActions.OnSignupButtonClick) },
            buttonText = "Sign up"
        )

        Spacer(modifier = Modifier.height(7.dp))

        ValidationErrorBox(uiState)
    }
}

@Composable
fun ValidationErrorBox(uiState: SignupUiState) {
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