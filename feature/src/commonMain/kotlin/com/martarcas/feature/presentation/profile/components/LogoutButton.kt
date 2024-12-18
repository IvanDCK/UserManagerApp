package com.martarcas.feature.presentation.profile.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.martarcas.feature.presentation.list.components.MultipurposeButton
import com.martarcas.feature.presentation.profile.ProfileViewModel
import com.martarcas.feature.presentation.profile.model.ProfileActions
import com.martarcas.feature.presentation.ui_utils.ClosedNight
import org.jetbrains.compose.resources.painterResource
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.logout_light

@Composable
fun LogoutButton(profileViewModel: ProfileViewModel, navigateToLogin: () -> Unit) {
    MultipurposeButton(
        modifier = Modifier.padding(end = 12.dp),
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