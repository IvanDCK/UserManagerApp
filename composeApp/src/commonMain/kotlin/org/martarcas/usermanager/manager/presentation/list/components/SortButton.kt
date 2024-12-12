package org.martarcas.usermanager.manager.presentation.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.martarcas.usermanager.core.presentation.Sunny
import org.martarcas.usermanager.manager.presentation.list.model.UserListAction
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.sort_ascending
import usermanagerapp.composeapp.generated.resources.sort_descending

@Composable
fun SortButton(
    sortAscending: Boolean,
    onAction: (UserListAction) -> Unit
) {
    if (sortAscending) {
        MultipurposeButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = {
                onAction(UserListAction.OnSortIconClick)
            },
            startIcon = painterResource(resource = Res.drawable.sort_ascending),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp),
            iconTint = Color.Black,
            buttonColor = Sunny,
            contentDescription = "Sort button icon"
        )
    } else {
        MultipurposeButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = {
                onAction(UserListAction.OnSortIconClick)
            },
            startIcon = painterResource(resource = Res.drawable.sort_descending),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp),
            iconTint = Color.Black,
            buttonColor = Sunny,
            contentDescription = "Sort button icon"
        )
    }
}