package org.martarcas.usermanager

import androidx.compose.ui.window.ComposeUIViewController
import org.martarcas.usermanager.presentation.app.App

fun MainViewController() = ComposeUIViewController { App() }