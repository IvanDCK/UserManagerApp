package org.martarcas.usermanager.app.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.core.navigation.NavigationWrapper
import org.martarcas.usermanager.core.presentation.DarkColorScheme
import org.martarcas.usermanager.core.presentation.LightColorScheme

@Composable
fun App(splashViewModel: AppViewModel = koinViewModel()) {

    val colors by mutableStateOf(if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme)
    val shouldStartFromList by splashViewModel.shouldStartFromList.collectAsState()

    MaterialTheme(colorScheme = colors) {

        NavigationWrapper(shouldStartFromList)

    }
}