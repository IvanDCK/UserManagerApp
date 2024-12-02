package org.martarcas.usermanager.core.presentation

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Cloudy,
    primaryContainer = MateDarkGray,
    secondary = Hazy,
    background = MateBlack,
    onBackground = Color.White,
    onPrimaryContainer = Color.White,
    tertiary = Gray,
    onTertiary = Color.White,
)

val LightColorScheme = lightColorScheme(
    primary = ClosedNight,
    primaryContainer = Color.White,
    secondary = Night,
    background = White,
    onBackground = Color.Black,
    onPrimaryContainer = Color.Black,
    tertiary = Gray,
    onTertiary = Gray,
)