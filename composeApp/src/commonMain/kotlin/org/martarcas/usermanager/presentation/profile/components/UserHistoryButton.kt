package org.martarcas.usermanager.presentation.profile.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.martarcas.usermanager.presentation.list.components.MultipurposeButton
import org.martarcas.usermanager.presentation.ui_utils.ClosedNight
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.list_icon

@Composable
fun UserHistoryButton(navigateToUserHistory: () -> Unit) {
    MultipurposeButton(
        onClick = {
            navigateToUserHistory()
        },
        startIcon = painterResource(resource = Res.drawable.list_icon),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp),
        iconTint = Color.White,
        buttonColor = ClosedNight,
        contentDescription = "user_history_button"
    )
}