package org.martarcas.usermanager.presentation.profile.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.martarcas.usermanager.presentation.ui_utils.Sunny

@Composable
fun DeleteUserDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Confirm Deletion")
        },
        text = {
            Text(
                text = "Are you sure you want to delete your account?\nThis action cannot be undone."
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