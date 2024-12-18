package com.martarcas.feature.presentation.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.martarcas.feature.presentation.list.model.UserListAction
import org.jetbrains.compose.resources.painterResource
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.sort_ascending
import usermanagerapp.feature.generated.resources.sort_descending

@Composable
fun SortButton(
    sortAscending: Boolean,
    onAction: (UserListAction) -> Unit
) {
    MultipurposeButton(
        modifier = Modifier.padding(end = 8.dp).height(50.dp),
        onClick = {
            onAction(UserListAction.OnSortIconClick)
        },
        startIcon = painterResource(resource = if (sortAscending) Res.drawable.sort_ascending else Res.drawable.sort_descending),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 4.dp),
        iconTint = Color.White,
        buttonColor = MaterialTheme.colorScheme.secondary,
        contentDescription = "Sort button icon"
    )
}