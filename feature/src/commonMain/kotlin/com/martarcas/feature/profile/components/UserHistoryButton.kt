package com.martarcas.feature.profile.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.martarcas.feature.list.components.MultipurposeButton
import com.martarcas.feature.ui_utils.ClosedNight
import org.jetbrains.compose.resources.painterResource
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.list_icon

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