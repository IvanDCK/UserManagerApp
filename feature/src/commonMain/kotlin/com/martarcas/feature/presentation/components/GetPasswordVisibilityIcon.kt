package com.martarcas.feature.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.pass_no_visible_dark
import usermanagerapp.feature.generated.resources.pass_no_visible_light
import usermanagerapp.feature.generated.resources.pass_visible_dark
import usermanagerapp.feature.generated.resources.pass_visible_light

@Composable
fun getPasswordVisibilityIcon(isPasswordVisible: Boolean): DrawableResource {
    val isDarkMode = isSystemInDarkTheme()
    return when {
        isPasswordVisible && isDarkMode -> Res.drawable.pass_visible_light
        !isPasswordVisible && isDarkMode -> Res.drawable.pass_no_visible_light
        isPasswordVisible && !isDarkMode -> Res.drawable.pass_visible_dark
        else -> Res.drawable.pass_no_visible_dark
    }
}
