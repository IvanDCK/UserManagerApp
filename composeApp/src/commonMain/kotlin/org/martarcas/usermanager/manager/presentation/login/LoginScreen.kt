package org.martarcas.usermanager.manager.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.core.presentation.TextFieldColor
import org.martarcas.usermanager.manager.presentation.login.model.LoginActions
import org.martarcas.usermanager.manager.presentation.login.model.LoginUiState
import org.martarcas.usermanager.manager.presentation.signup.components.AuthHeader
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.header_signup
import usermanagerapp.composeapp.generated.resources.pass_no_visible_dark
import usermanagerapp.composeapp.generated.resources.pass_no_visible_light
import usermanagerapp.composeapp.generated.resources.pass_visible_dark
import usermanagerapp.composeapp.generated.resources.pass_visible_light

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Don't have an account?",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    modifier = Modifier
                        .clickable {
                            loginViewModel.onAction(LoginActions.OnNotHaveAccountClick)
                        },
                    text = "Register here",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 13.sp
                )
            }
        }
    ) { innerPadding ->
        LoginContent(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(
                rememberScrollState()
            ),
            viewModel = loginViewModel,
            uiState = uiState
        )
    }
}

@Composable
fun LoginContent(modifier: Modifier, viewModel: LoginViewModel, uiState: LoginUiState) {
    val textFieldModifier = Modifier
        .fillMaxWidth(0.9f)
        .height(70.dp)

    val isPasswordVisible = uiState.isPasswordVisible
    val isDarkMode = isSystemInDarkTheme()

    val iconRes = when {
        isPasswordVisible && isDarkMode -> Res.drawable.pass_visible_light
        !isPasswordVisible && isDarkMode -> Res.drawable.pass_no_visible_light
        isPasswordVisible && !isDarkMode -> Res.drawable.pass_visible_dark
        else -> Res.drawable.pass_no_visible_dark
    }


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

        OutlinedTextField(
            modifier = textFieldModifier,
            value = uiState.email,
            onValueChange = {
                viewModel.onAction(LoginActions.OnEmailChange(it))
            },
            label = { Text("Email", color = TextFieldColor) },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            modifier = textFieldModifier,
            value = uiState.password,
            onValueChange = {
                viewModel.onAction(LoginActions.OnPasswordChange(it))
            },
            label = { Text("Password", color = TextFieldColor) },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            maxLines = 1,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    modifier = Modifier.padding(end = 5.dp),
                    onClick = {
                        viewModel.onAction(LoginActions.OnPasswordVisibleClick)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(iconRes),
                        contentDescription = "Toggle password visibility",
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Checkbox(
                checked = uiState.rememberMeIsChecked,
                onCheckedChange = {
                    viewModel.onAction(LoginActions.OnRememberMeChange)
                }
            )
            Text("Remember me")
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            modifier = textFieldModifier,
            onClick = {
                viewModel.onAction(LoginActions.OnLoginButtonClick)
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            if (uiState.isLoadingOnClick) CircularProgressIndicator()
            else Text("Log in", color = Color.White, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(7.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                if (uiState.validationErrors.isNotEmpty()) {
                    uiState.validationErrors.forEach { error ->
                        Text(
                            text = "* $error",
                            color = Color.Red.copy(0.7f),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Light
                        )
                    }
                } else if (uiState.errorMessage != null) {
                    Text(
                        text = uiState.errorMessage.asString(),
                        color = Color.Red.copy(0.7f),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}
