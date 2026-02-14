package com.pummarola.studypal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AccentBlue,
    secondary = AccentBlue,
    tertiary = AccentLightBlue,
    background = BlackText // Sfondo scuro per la Dark Mode
)

private val LightColorScheme = lightColorScheme(
    primary = AccentBlue,
    secondary = AccentBlue,
    tertiary = AccentLightBlue,
    background = LightGrayBackground // Sfondo quasi bianco per la Light Mode
)

@Composable
fun StudyPalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}