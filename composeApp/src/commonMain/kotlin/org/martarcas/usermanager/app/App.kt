package org.martarcas.usermanager.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.martarcas.usermanager.core.navigation.NavigationWrapper
import org.martarcas.usermanager.core.presentation.DarkColorScheme
import org.martarcas.usermanager.core.presentation.LightColorScheme

@Composable
@Preview
fun App() {
    MaterialTheme {
        val colors by mutableStateOf(if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme )

        MaterialTheme(colorScheme = colors) {
            NavigationWrapper()

        }
    }
}