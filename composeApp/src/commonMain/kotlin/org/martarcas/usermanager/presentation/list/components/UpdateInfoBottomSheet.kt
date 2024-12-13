package org.martarcas.usermanager.presentation.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.martarcas.usermanager.presentation.list.model.UserListState
import usermanagerapp.composeapp.generated.resources.Res
import usermanagerapp.composeapp.generated.resources.pass_no_visible_dark
import usermanagerapp.composeapp.generated.resources.pass_visible_dark


@Composable
fun BottomSheetMultiplatform(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (isOpen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    onClick = onDismiss,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                AnimatedVisibility(
                    visible = isOpen,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}


@Composable
fun BottomSheetContent(
    state: UserListState,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordToggle: () -> Unit,
    onUpdateInfo: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Update information",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(8.dp))

        val textFieldModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)

        // Name
        OutlinedTextField(
            modifier = textFieldModifier,
            value = state.name,
            onValueChange = {
                onNameChange(it)
            },
            label = { Text("Name", color = MaterialTheme.colorScheme.onSurface) },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            maxLines = 1
        )

        // Surname
        OutlinedTextField(
            modifier = textFieldModifier,
            value = state.surname,
            onValueChange = {
                onSurnameChange(it)
            },
            label = { Text("Surname", color = MaterialTheme.colorScheme.onSurface) },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            maxLines = 1
        )

        // Email
        OutlinedTextField(
            modifier = textFieldModifier,
            value = state.email,
            onValueChange = {
                onEmailChange(it)
            },
            label = { Text("Email", color = MaterialTheme.colorScheme.onSurface) },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            maxLines = 1
        )

        // Password
        val isPasswordVisible = state.isPasswordVisible
        val iconRes = if (isPasswordVisible) Res.drawable.pass_no_visible_dark else Res.drawable.pass_visible_dark

        OutlinedTextField(
            modifier = textFieldModifier,
            value = state.password,
            onValueChange = {
                onPasswordChange(it)
            },
            label = { Text("Password", color = MaterialTheme.colorScheme.onSurface) },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            maxLines = 1,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    modifier = Modifier.padding(end = 5.dp),
                    onClick = {
                        onPasswordToggle()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(iconRes),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Toggle password visibility",
                    )
                }
            }
        )

        Button(
            modifier = textFieldModifier,
            onClick = {
                onUpdateInfo()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            if (state.isUpdateInfoLoading) CircularProgressIndicator(color = Color.White)
            else Text("Update", color = Color.White, fontSize = 20.sp)
        }
        if (state.bottomSheetErrorMessage != null) {
            Text(
                text = state.bottomSheetErrorMessage.asString(),
                color = MaterialTheme.colorScheme.error,
                fontSize = 16.sp
            )
        }
    }
}
