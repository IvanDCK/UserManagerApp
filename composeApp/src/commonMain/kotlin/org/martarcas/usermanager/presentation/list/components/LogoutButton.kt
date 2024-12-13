package org.martarcas.usermanager.presentation.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.martarcas.usermanager.presentation.list.model.UserListAction
import org.martarcas.usermanager.presentation.ui_utils.Sunny
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.logout_dark

@Composable
fun LogoutButton(onAction: (UserListAction) -> Unit, navigateToLogin: () -> Unit) {
    MultipurposeButton(
        modifier = Modifier.padding(end = 8.dp),
        onClick = {
            onAction(UserListAction.OnLogoutClick)
            navigateToLogin()
        },
        startIcon = painterResource(resource = Res.drawable.logout_dark),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp),
        iconTint = Color.Black,
        buttonColor = Sunny,
        contentDescription = "logout_button"
    )
}