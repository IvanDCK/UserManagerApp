package org.martarcas.usermanager.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.martarcas.usermanager.presentation.components.AuthTextField
import org.martarcas.usermanager.presentation.components.ProgressLoading
import org.martarcas.usermanager.presentation.components.getPasswordVisibilityIcon
import org.martarcas.usermanager.presentation.profile.components.ChooseAvatarBottomSheet
import org.martarcas.usermanager.presentation.profile.components.DeleteUserDialog
import org.martarcas.usermanager.presentation.profile.components.LogoutButton
import org.martarcas.usermanager.presentation.profile.components.ProfileButton
import org.martarcas.usermanager.presentation.profile.components.UserAvatar
import org.martarcas.usermanager.presentation.profile.components.UserHistoryButton
import org.martarcas.usermanager.presentation.profile.model.DeleteDialogActions
import org.martarcas.usermanager.presentation.profile.model.ProfileActions
import org.martarcas.usermanager.presentation.profile.model.ProfileUiState
import org.martarcas.usermanager.presentation.ui_utils.ClosedNight
import org.martarcas.usermanager.presentation.ui_utils.Hazy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, navigateToLogin: () -> Unit, navigateToUserHistory: () -> Unit) {

    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    UserHistoryButton(navigateToUserHistory)
                },
                actions = {
                    LogoutButton(profileViewModel, navigateToLogin)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            // Only show when avatar edit button is clicked
            ChooseAvatarBottomSheet(profileViewModel)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { ProfileScreenContent(uiState, profileViewModel){
            navigateToLogin()
        } }
    }
}

@Composable
fun ProfileScreenContent(uiState: ProfileUiState, profileViewModel: ProfileViewModel, navigateToLogin: () -> Unit) {

    val loggedUser = uiState.loggedUser

    val textFieldModifier = Modifier
        .fillMaxWidth(0.9f)
        .height(80.dp)

    val passwordVisibilityIcon =
        getPasswordVisibilityIcon(isPasswordVisible = uiState.isPasswordVisible)

    when {
        uiState.isLoading -> {
            ProgressLoading()
        }

        uiState.showRemoveAccountPopup -> {
            DeleteUserDialog(
                onConfirm = {
                    profileViewModel.onDeleteDialogAction(DeleteDialogActions.OnConfirmClick)
                    navigateToLogin()
                },
                onDismiss = { profileViewModel.onDeleteDialogAction(DeleteDialogActions.OnDismissClick) }
            )
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserAvatar(uiState.avatarId, profileViewModel)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "${loggedUser?.name} ${loggedUser?.surname}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = loggedUser?.role?.formattedName.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Hazy,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                AuthTextField(
                    modifier = textFieldModifier,
                    value = uiState.firstNameText,
                    onValueChange = { text ->
                        profileViewModel.onProfileAction(ProfileActions.OnFirstNameChange(text))
                    },
                    label = "First Name"
                )

                Spacer(modifier = Modifier.height(15.dp))

                AuthTextField(
                    modifier = textFieldModifier,
                    value = uiState.lastNameText,
                    onValueChange = { text ->
                        profileViewModel.onProfileAction(ProfileActions.OnLastNameChange(text))
                    },
                    label = "Last Name"
                )
                Spacer(modifier = Modifier.height(15.dp))

                AuthTextField(
                    modifier = textFieldModifier,
                    value = uiState.emailText,
                    onValueChange = { text ->
                        profileViewModel.onProfileAction(ProfileActions.OnEmailChange(text))
                    },
                    label = "Email"
                )

                Spacer(modifier = Modifier.height(15.dp))

                AuthTextField(
                    modifier = textFieldModifier,
                    value = uiState.passwordText,
                    onValueChange = { text ->
                        profileViewModel.onProfileAction(ProfileActions.OnPasswordChange(text))
                    },
                    label = "Password",
                    visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { profileViewModel.onProfileAction(ProfileActions.OnPasswordVisibleClick) }) {
                            Icon(
                                painter = painterResource(passwordVisibilityIcon),
                                contentDescription = "Toggle password visibility",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                ProfileButton(
                    label = "Save changes",
                    backgroundColor = ClosedNight,
                    onClick = {
                        profileViewModel.onProfileAction(ProfileActions.OnSaveButtonClick)
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                ProfileButton(
                    label = "Delete account",
                    backgroundColor = Color(0xFF7B2223),
                    onClick = {
                        profileViewModel.onProfileAction(ProfileActions.OnRemoveAccountButtonClick)
                    }
                )
            }
        }
    }
}