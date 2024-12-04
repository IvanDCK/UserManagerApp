package org.martarcas.usermanager.app.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.core.navigation.NavigationWrapper
import org.martarcas.usermanager.core.presentation.DarkColorScheme
import org.martarcas.usermanager.core.presentation.LightColorScheme

@Composable
@Preview
fun App() {
    MaterialTheme {
        val colors by mutableStateOf(if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme)

        MaterialTheme(colorScheme = colors) {

            var startDestination by remember { mutableStateOf("") }
            val splashViewModel: AppViewModel = koinViewModel()
            val shouldStartFromList by splashViewModel.shouldStartFromList.collectAsState()

            startDestination = if (shouldStartFromList) "List" else "Login"

            NavigationWrapper(startDestination)
        }
    }
}