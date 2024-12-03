package org.martarcas.usermanager.manager.presentation.list.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.martarcas.usermanager.core.presentation.Sunny
import org.martarcas.usermanager.manager.domain.model.user.UserPublic

@Composable
fun DeleteUserDialog(
    userToDelete: UserPublic,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Confirm Deletion")
        },
        text = {
            Text(
                text = "Are you sure you want to delete the user \"${userToDelete.name} ${userToDelete.surname}\"?\nThis action cannot be undone."
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Delete", color = Sunny)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
    
}