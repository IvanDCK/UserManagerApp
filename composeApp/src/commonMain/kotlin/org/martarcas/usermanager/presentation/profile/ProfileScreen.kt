package org.martarcas.usermanager.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.martarcas.usermanager.presentation.components.AuthTextField
import org.martarcas.usermanager.presentation.components.getPasswordVisibilityIcon
import org.martarcas.usermanager.presentation.list.components.LogoutButton
import org.martarcas.usermanager.presentation.profile.components.ChooseAvatarBottomSheet
import org.martarcas.usermanager.presentation.profile.components.ProfileButton
import org.martarcas.usermanager.presentation.profile.components.UserAvatar
import org.martarcas.usermanager.presentation.profile.model.ProfileActions
import org.martarcas.usermanager.presentation.profile.model.ProfileUiState
import org.martarcas.usermanager.presentation.ui_utils.ClosedNight
import org.martarcas.usermanager.presentation.ui_utils.Hazy
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.user_avatar0
import usermanagerapp.composeapp.generated.resources.user_avatar1
import usermanagerapp.composeapp.generated.resources.user_avatar2
import usermanagerapp.composeapp.generated.resources.user_avatar3
import usermanagerapp.composeapp.generated.resources.user_avatar4
import usermanagerapp.composeapp.generated.resources.user_avatar5
import usermanagerapp.composeapp.generated.resources.user_avatar6
import usermanagerapp.composeapp.generated.resources.user_avatar7

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, navigateToLogin: () -> Unit) {

    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
        ) { ProfileScreenContent(uiState, profileViewModel) }
    }
}

@Composable
fun ProfileScreenContent(uiState: ProfileUiState, profileViewModel: ProfileViewModel) {

    val loggedUser = uiState.loggedUser

    val textFieldModifier = Modifier
        .fillMaxWidth(0.9f)
        .height(80.dp)

    val passwordVisibilityIcon =
        getPasswordVisibilityIcon(isPasswordVisible = uiState.isPasswordVisible)

    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
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


fun String.toDrawableProfileImage(): DrawableResource {
    return when (this) {
        "user_avatar0" -> return Res.drawable.user_avatar0
        "user_avatar1" -> return Res.drawable.user_avatar1
        "user_avatar2" -> return Res.drawable.user_avatar2
        "user_avatar3" -> return Res.drawable.user_avatar3
        "user_avatar4" -> return Res.drawable.user_avatar4
        "user_avatar5" -> return Res.drawable.user_avatar5
        "user_avatar6" -> return Res.drawable.user_avatar6
        "user_avatar7" -> return Res.drawable.user_avatar7
        else -> return Res.drawable.user_avatar0
    }
}