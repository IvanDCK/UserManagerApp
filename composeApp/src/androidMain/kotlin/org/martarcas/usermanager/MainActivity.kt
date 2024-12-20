package org.martarcas.usermanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.martarcas.feature.presentation.AppViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.presentation.App

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setContent {
                val splashViewModel: AppViewModel = koinViewModel()
                setKeepOnScreenCondition(condition = { splashViewModel.splashCondition.value })
                App(splashViewModel)
            }
        }

    }
}
