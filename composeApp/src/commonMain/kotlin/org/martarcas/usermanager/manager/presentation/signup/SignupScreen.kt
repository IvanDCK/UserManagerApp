package org.martarcas.usermanager.manager.presentation.signup

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
import org.martarcas.usermanager.manager.presentation.signup.components.AuthHeader
import org.martarcas.usermanager.manager.presentation.signup.model.SignupActions
import org.martarcas.usermanager.manager.presentation.signup.model.SignupUiState
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.header_signup
import usermanagerapp.composeapp.generated.resources.pass_no_visible_dark
import usermanagerapp.composeapp.generated.resources.pass_no_visible_light
import usermanagerapp.composeapp.generated.resources.pass_visible_dark
import usermanagerapp.composeapp.generated.resources.pass_visible_light

@Composable
fun SignUpScreen(
    navigateToLogin: () -> Unit
) {
    val signupViewModel: SignUpViewModel = koinViewModel()

    val uiState by signupViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.shouldNavigateToLogin) {
            navigateToLogin()
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
                    "Already have an account?",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    modifier = Modifier
                        .clickable {
                            signupViewModel.onAction(SignupActions.OnAlreadyHaveAccountClick)
                        },
                    text = "Login here",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 13.sp
                )
            }
        }
    ) { innerPadding ->
        SignUpContent(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(
                rememberScrollState()
            ),
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
            title = "Sign in to your",
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = textFieldModifier,
            value = uiState.firstName,
            onValueChange = {
                viewModel.onAction(SignupActions.OnFirstNameChange(it))
            },
            label = { Text("First Name", color = TextFieldColor) },
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
            value = uiState.lastName,
            onValueChange = {
                viewModel.onAction(SignupActions.OnLastNameChange(it))
            },
            label = { Text("Last Name", color = TextFieldColor, textAlign = TextAlign.Center) },
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
            value = uiState.email,
            onValueChange = {
                viewModel.onAction(SignupActions.OnEmailChange(it))
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
                viewModel.onAction(SignupActions.OnPasswordChange(it))
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
                        viewModel.onAction(SignupActions.OnPasswordVisibleClick)
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
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = textFieldModifier,
            onClick = {
                viewModel.onAction(SignupActions.OnSignupButtonClick)
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Sign up", color = Color.White, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(7.dp))
        if (uiState.validationErrors.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
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
                }
            }
        }

    }
}