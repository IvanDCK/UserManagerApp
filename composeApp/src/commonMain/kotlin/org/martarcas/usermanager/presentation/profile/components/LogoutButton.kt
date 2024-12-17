package org.martarcas.usermanager.presentation.profile.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.martarcas.usermanager.presentation.list.components.MultipurposeButton
import org.martarcas.usermanager.presentation.profile.ProfileViewModel
import org.martarcas.usermanager.presentation.profile.model.ProfileActions
import org.martarcas.usermanager.presentation.ui_utils.ClosedNight
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.logout_light

@Composable
fun LogoutButton(profileViewModel: ProfileViewModel, navigateToLogin: () -> Unit) {
    MultipurposeButton(
        modifier = Modifier.padding(end = 8.dp),
        onClick = {
            profileViewModel.onProfileAction(ProfileActions.OnLogoutButtonClick)
            navigateToLogin()
        },
        startIcon = painterResource(resource = Res.drawable.logout_light),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp),
        iconTint = Color.White,
        buttonColor = ClosedNight,
        contentDescription = "logout_button"
    )
}