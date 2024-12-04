package org.martarcas.usermanager

import androidx.compose.ui.window.ComposeUIViewController
import org.martarcas.usermanager.app.presentation.App

fun MainViewController() = ComposeUIViewController { App() }