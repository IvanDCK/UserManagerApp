package org.martarcas.usermanager.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.martarcas.feature.presentation.AppViewModel
import com.martarcas.feature.presentation.ui_utils.DarkColorScheme
import com.martarcas.feature.presentation.ui_utils.LightColorScheme
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.navigation.NavigationWrapper

@Composable
fun App(splashViewModel: AppViewModel = koinViewModel()) {

    val colors by mutableStateOf(if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme)
    val shouldStartFromList by splashViewModel.shouldStartFromList.collectAsState()

    MaterialTheme(colorScheme = colors) {
        NavigationWrapper(shouldStartFromList)
    }
}